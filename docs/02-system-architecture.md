# Family Reminder App – System Architecture

## Guiding Principles
- **Clean Architecture:** طبقات واضحة (Presentation ← Application ← Domain ← Infrastructure) مع حدود واضحة وعقود Interfaces.
- **SOLID:** 
  - SRP: فصل خدمات التذكير، المستخدم، الإشعار.
  - OCP: دعم إضافة قنوات تنبيه بدون تعديل منطق الأعمال.
  - LSP: واجهات Domain واضحة لتسهيل استبدال Implementations.
  - ISP: تقسيم واجهات الخدمات لكل حالة استخدام (مثل `ReminderScheduler`، `NotificationSender`).
  - DIP: الاعتماد على Abstractions مع حقن الاعتمادية (Spring Boot + Koin/DI في KMP).
- **Scalability:** خدمات قابلة للتمديد أفقيًا، استخدام Messaging للأعمال الحساسة للوقت، Stateless قدر الإمكان.

## High-Level Components
```
KMP Clients (Mobile iOS/Android, Web) 
   |  (Ktor Client over HTTPS + WebSockets)
API Gateway / BFF (Spring Boot)
   |-- Reminder Service (Spring Boot, Hexagonal)
   |-- Family/User Service (Spring Boot)
   |-- Notification Service (Spring Boot, async)
   |-- Location Context Service (Spring Boot, optional)
   |-- Auth Service (Keycloak أو Spring Authorization Server)
Datastores: PostgreSQL, Redis Cache, S3-compatible object storage
Messaging: Kafka (or RabbitMQ) لجدولة التذكيرات
Observability Stack: Prometheus + Grafana + ELK
```

## Backend Layering (Spring Boot)
- **Domain Layer (`core-domain` module):** كيانات `Reminder`, `Family`, `Member`, Value Objects (`ReminderId`, `GeoFence`). يحوي Services غنية بالمنطق بدون علم بالبنية التحتية.
- **Application Layer (`app-service` module):** Use Cases مثل `CreateReminder`, `CompleteReminder`. ينفذ احتياجات المعاملات، يبدأ Flows، ويتكامل مع الـ Ports.
- **Infrastructure Layer (`infra` module):** Adapters لـ:
  - Persistence (Spring Data + PostgreSQL)
  - Messaging (Spring Cloud Stream + Kafka)
  - Notification Channels (FCM, SendGrid, Twilio)
  - Geo Service (Google Maps/Mapbox Geocoding)

## Shared Modules (KMP)
- **Shared Domain:** نماذج Reminder/Family + Validation مشتركة.
- **Networking Module:** client abstractions لـ REST/WebSocket.
- **Location Module:** التفاف حول APIs الموقع، مع fallbacks لكل منصة.
- **UI:** Compose Multiplatform للـ Mobile + Compose for Web (أو React via Kotlin/JS) تبعًا لأولويات الفريق.

## Data Flow (Create Reminder)
1. المستخدم ينشئ Reminder عبر تطبيق الهاتف (KMP).
2. الطلب يصل إلى API Gateway → يوجهه إلى Reminder Service.
3. Use Case `CreateReminder` يتحقق من الصلاحيات، يثبت العمل في Transaction، ينشر Event `ReminderCreated`.
4. Notification Service يلتقط الحدث، يبرمج Job في Kafka (أو Quartz) للوقت المحدد.
5. عند استيفاء شرط الوقت/الموقع، يتم إرسال تنبيه عبر القناة المناسبة.
6. يتم تسجيل الحدث في Event Store (للتحليلات) وتحديث الـ cache.

## Persistence Strategy
- **Relational Core (PostgreSQL):** جداول مترابطة للأسر، الأعضاء، التذكيرات، الأحداث.
- **Redis:** تخزين جلسات، Locks، جدولة قصيرة المدى للتذكيرات.
- **S3/MinIO:** مرفقات الملاحظات (صور، ملفات PDF).
- **Time-series Metrics:** Prometheus exporters داخل الخدمات.

## Location-based Reminder Design
- واجهة `LocationTriggerEvaluator` في Domain.
- Adapter للبنية التحتية يستعمل Geofencing (على الهاتف) + Webhook للـ backend:
  - الهواتف ترسل أحداث `Enter/Exit` للـ Backend (JWM/WorkManager على Android، Region Monitoring على iOS).
  - لمنصات الويب، يُستخدم polling أو Web Geolocation API.
- حماية الخصوصية: خيار المستخدم لتفعيل الميزة، إعدادات حذف التاريخ الجغرافي.

## Security
- OAuth2/OIDC مع Keycloak (Self-hosted) أو Auth0.
- RBAC مبني على أدوار الأسرة + Claims مخصصة لكل Reminder.
- Rate limiting عبر API Gateway (Spring Cloud Gateway أو Kong).
- تشفير البيانات الحساسة (Transparent Column Encryption، Secrets في HashiCorp Vault).

## Deployment View
- كل خدمة Spring Boot داخل Docker Image مبنية بـ Gradle.
- Kubernetes:
  - Namespace `family-reminder`.
  - Deployments منفصلة لكل خدمة + HPA.
  - ConfigMaps / Secrets.
  - Ingress Controller (NGINX).
- Observability:
  - Fluent Bit → Elasticsearch.
  - Prometheus Operator + Grafana dashboards.

## Integration & Testing Strategy
- **Contract Tests:** Pact بين KMP client و API.
- **Integration Tests:** TestContainers لقاعدة البيانات و Kafka.
- **End-to-End:** تشغيل بيئة مصغرة عبر Docker Compose.
- **Performance Tests:** Gatling/K6 قبل الإنتاج.
