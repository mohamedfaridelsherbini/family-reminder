# Family Reminder App – Git Workflow & Branching Strategy

## Goals
- تسهيل العمل التعاوني بدون صدامات أو تأخير.
- الحفاظ على فرع `main` في حالة قابلة للنشر دائمًا.
- توفير مسار واضح لطلبات المراجعة، الفحص، والإصدارات.

## Branch Model
- **`main`:** الفرع المستقر؛ يحتوي فقط على كود مجرّب ومصادق عليه. يُنشر تلقائيًا إلى بيئة `staging` أو `prod` حسب الإعدادات.
- **`develop`:** (اختياري) فرع تجميعي لأعمال قصيرة الأجل عندما يكون عدد الفرق كبيرًا. يمكن الاستغناء عنه في حال اتباع `trunk-based`.
- **Feature Branches:** `feature/<scope>-<short-description>`
  - تنطلق من `main` (أو `develop` إذا أُعتمدت).
  - مدة الحياة قصيرة (≤ 3 أيام عمل).
  - يُدمج عبر Pull Request بعد مراجعة + موافقة واحدة على الأقل.
- **Fix Branches:** 
  - أخطاء حرجة في الإنتاج: `hotfix/<issue-id>` تنطلق من `main`.
  - إصلاحات صغيرة على `develop`: `bugfix/<issue-id>` تنطلق من `develop`.
- **Release Branches:** `release/<version>` عند تجميد المزايا وتحضير الإصدار. تستقبل إصلاحات بسيطة فقط، بعدها تدمج في `main` و `develop`.

## Commit Strategy
- **Messages:** صيغة `type(scope): summary`
  - `feat`, `fix`, `refactor`, `docs`, `test`, `chore`, `build`.
  - مثال: `feat(reminder): add geofence trigger evaluator`.
- الكوميتات صغيرة ومحددة؛ لا تتجاوز ملفّات غير مرتبطة.
- ارفاق رقم التذكرة إن وُجد: `fix(auth): handle expired refresh token (#123)`.

## Pull Requests
- **المحتوى:** وصف الهدف، اللقطات أو روابط التصميم، خطوات الاختبار، تأثير على الـ CI/CD.
- **المراجعة:** مطلوب مراجعة واحدة على الأقل + موافقة Owner عند تغييرات البنية.
- **الفحوصات:** 
  - Static Analysis (ktlint/detekt).
  - Unit/Integration Tests حسب الطبقة.
  - SonarQube Quality Gate.
  - فحص أمني (Trivy/Dependabot إن وجد).
- **معايير الدمج:** لا يوجد فشل في الفحوصات، عدم وجود تعارضات، مراجعة مكتملة.

## Tagging & Releases
- استخدام **SemVer** (`v1.2.0`).
- الوسوم تنشأ من `main` بعد اكتمال اختبار الإصدار.
- Jenkins pipeline تُفعّل نشر الصور/المخططات تلقائيًا عند إنشاء الوسم.

## Branch Protection Rules
- حماية `main`: ممنوع الدفع المباشر، يتطلب PR + نجاح جميع الفحوصات.
- حماية `release/*`: يتطلب موافقة اثنين، ويمنع Force Push.
- تفعيل `Require status checks to pass before merging` على GitHub/GitLab.

## Working Agreements
- سحب (`git pull --rebase`) أو `git fetch` + `rebase` قبل الفتح PR لتقليل التعارضات.
- حذف الفروع بعد الدمج للحفاظ على نظافة المستودع.
- توثيق التغييرات الكبيرة في `docs/`، وتحديث الـ Roadmap حسب الحاجة.
- اجتماع عملية أسبوعي سريع لمراجعة حالة الفروع المفتوحة والمتعثرة.

## Tooling & Automation
- اعتمد قوالب PR (`.github/pull_request_template.md`) لتوحيد المتطلبات.
- استخدم Git Hooks محلية (`pre-commit`) لتشغيل linters/formatters تلقائيًا.
- فعّل Dependabot أو Renovate لتحديث التبعيات، مع فرع `chore/deps-<date>`.
- تتبع على Jira/Linear: التسليم (`Done`) يتم فقط عند دمج الفرع + نجاح النشر في البيئة المستهدفة.

## Scaling Considerations
- عند تعدد الفرق، يمكن تقسيم الريبو إلى mono-repo مع Workspaces أو multi-repo حسب حجم الخدمات.
- إدخال GitFlow الكامل أو trunk-based يعتمد على معدل الإصدارات؛ ابدأ بـ trunk-based ثم أضف release branches عند الحاجة.
- مع مرور الوقت، يمكن أتمتة دمج الـ dependabot وسحب الإصلاحات الأمنية بشكل يومي.

## Repository & Domain Setup
- **Repositories على GitHub (`github.com/mohamedfaridelsherbini`):**
  - `personal-website`: الكود الحالي لـ `www.mohamedfaridelsherbini.com`.
  - `family-reminder-backend`: خدمات Spring Boot + البنية التحتية (Helm، Docker، Jenkinsfile).
  - `family-reminder-admin`: واجهة الويب الإدارية (SPA أو KMP web) تُنشر على `admin.mohamedfaridelsherbini.com`.
  - *(اختياري لاحقًا)* `family-reminder-app`: مشروع KMP للموبايل/الويب العام.
- **DNS Records:**
  - `A` لـ `www` يشير إلى ‎`161.35.20.4`‎ (موجود).
  - `A` لـ `api` → ‎`161.35.20.4`‎ (أو IP منفصل إذا وُجد).
  - `A` لـ `admin` → ‎`161.35.20.4`‎.
- **Reverse Proxy/Nginx على الدروبلت:**
  - إعداد Virtual Hosts لكل ساب دومين (`/etc/nginx/sites-available`).
  - توجيه `www` إلى الموقع الشخصي، `api` إلى خدمة Spring Boot (مثلاً :8080)، `admin` إلى واجهة الويب (:3000).
  - تفعيل HTTPS باستخدام `certbot --nginx` لكل دومين.
- **CI/CD:**
  - Jenkins Multibranch لكل repo مع الخطوات المذكورة في `docs/04-devops-platform.md`.
  - Secrets عبر Vault أو GitHub Actions Encrypted Secrets في حالة استخدام GitHub Actions.
- **خطوات التنفيذ الفورية:**
  1. إنشاء المستودعات الجديدة على GitHub مع README و LICENSE.
  2. تحديث سجلات DNS في موفر الدومين وإتاحة الوقت للـ propagation.
  3. ضبط Nginx + Certbot على الدروبلت (`personal-website`)، والتأكد من الخدمات تعمل خلف reverse proxy.
  4. ربط Jenkins أو GitHub Actions بالمستودعات وتشغيل أول Build/Test.
