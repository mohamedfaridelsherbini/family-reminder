# Family Reminder Monorepo

هذا المستودع يجمع مكونات تطبيق **Family Reminder** في مجلدات مستقلة لكل مشروع.

## الهيكل الحالي
- `docs/` – وثائق الرؤية، المعمارية، خطة التنفيذ، DevOps، Git workflow، وإعداد البنية التحتية.
- `projects/backend/` – كود Spring Boot والواجهات الخلفية.
- `projects/admin/` – لوحة التحكم (SPA أو KMP Web) للإدارة.
- `projects/mobile-app/` – تطبيق Kotlin Multiplatform (iOS/Android/Web) للمستخدمين النهائيين.
- `projects/devops/` – سكربتات النشر، Helm charts، Jenkins pipelines، وInfra-as-Code.

## الخطوات التالية
1. تهيئة كل مشروع داخل مجلده مع README خاص.
2. تطبيق استراتيجية Git الموضحة في `docs/05-git-branching.md` لكل ريبو أو مجلد فرعي حسب أسلوب العمل.
3. اتباع خطة الإعداد في `docs/06-infra-setup.md` لتجهيز البيئات والدومينات.

> ملاحظة: يمكن نقل كل مجلد إلى مستودع Git مستقل لاحقًا إذا تقرر الفصل الكامل بين المشاريع.
