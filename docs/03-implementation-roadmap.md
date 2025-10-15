# Family Reminder App – Implementation Roadmap

## Phase 0 – Alignment & Foundations (Week 0-1)
- تشكيل الفريق، تحديد المسؤوليات (Backend، KMP، DevOps، QA، UX).
- مراجعة Product Vision (docs/01-product-vision.md) وإغلاق الأسئلة المفتوحة مع Stakeholders.
- إعداد أدوات التواصل وإدارة المشروع (Jira/Linear، Slack، Notion).
- إرساء قواعد الكود: formatters، linters، قواعد PR، branching strategy (`trunk-based` أو `gitflow` المخفف).
- تجهيز Repos:
  - Backend (Spring Boot multi-module).
  - Shared KMP module.
  - DevOps (Helm/K8s manifests، Jenkins pipeline).

## Phase 1 – Product Discovery & Design (Week 1-3)
- ورش عمل User Journey، رسم Sequnce Diagrams.
- تصميم Wireframes و UI Kit على Figma.
- تعريف الـ API Contracts باستخدام OpenAPI + JSON Schema.
- مستند قبول UX + Accessibility checklist.
- إعداد خطة اختبار قبول المستخدم (UAT) المبدئية.

## Phase 2 – Platform Bootstrapping (Week 2-4)
- Backend:
  - إنشاء مشروع Gradle Kotlin DSL، توزيع modules (`core-domain`, `application`, `infrastructure`, `api`).
  - تنفيذ Auth Service مبدئي بـ Keycloak dev profile.
  - إعداد DB schema عبر Flyway.
- KMP:
  - إنشاء مشروع Compose Multiplatform + Ktor Client.
  - إعداد DI (Koin أو Kodein)، إدارة state (MVI/Redux-like).
- DevOps:
  - Dockerfiles للخدمات، Docker Compose للبيئات المحلية.
  - بناء Jenkinsfile أولي (Checkout، Build، Test، Publish Artifact إلى Nexus/Artifactory).

## Phase 3 – Core Reminder Experience (Week 4-7)
- تنفيذ Use Cases: إنشاء/تحديث/حذف Reminder، Assignment، Checklist.
- تكامل Notification Service مع FCM + Email.
- بناء الواجهة الرئيسية (قائمة التذكيرات، تفاصيل التذكير، إشعار النشاط).
- إعداد Audit Trail + Observability basics (Micrometer، Prometheus endpoints).
- اختبارات تكامل (TestContainers) + CI gate.

## Phase 4 – Family & Collaboration (Week 6-8)
- إدارة الأسرة: دعوات، قبول، أدوار.
- إنشاء Calendar View متزامن مع الـ backend.
- Activity Feed + Events analytics الأساسية.
- تطبيق صلاحيات RBAC في الـ backend + واجهات المستخدم.

## Phase 5 – Location-based Reminders (Week 8-10, Optional MVP+)
- تفعيل Geo-fencing على تطبيق الهاتف (Android/iOS).
- API لدعم حفظ GeoFence، إدارة Battery/Privacy.
- Notification Service يدعم Trigger by Location.
- Guardrails: throttling، consent management، حذف البيانات الجغرافية.

## Phase 6 – Hardening & Beta (Week 9-11)
- اختبارات أداء (K6/Gatling) والتكيف مع النتائج (Indexing، caching، scaling).
- Security Review (OWASP ASVS)، Pen-test خفيف.
- إعداد Beta Test عبر Firebase App Distribution/TestFlight.
- تحسين DX: scripts للتشغيل المحلي، Docs مكملة.

## Phase 7 – Launch Readiness (Week 11-12)
- مراقبة End-to-end (Grafana dashboards، Kibana dashboards).
- Training للمحتوى التسويقي + الدعم.
- إعداد خطة Incident Response (On-call، Runbook).
- مراجعة قانونية وسياسات الخصوصية.
- Soft Launch لعائلة داخلية + قياس المقاييس.

## Continuous Initiatives
- إدارة المعرفة: تحديث الوثائق، تسجيل الدروس المستفادة.
- Refactoring & Tech Debt Sprints كل 6 أسابيع.
- مراقبة تكلفة البنية التحتية (FinOps بسيط).
- تحسينات منتظمة على Accessibility والLocalization.
