# Family Reminder App – Infrastructure Setup Guide

## 1. GitHub Repositories
1. سجّل الدخول إلى `github.com/mohamedfaridelsherbini`.
2. أنشئ الريبوّات التالية باستخدام إعدادات عامة (Public/Private حسب الحاجة):
   - `family-reminder-backend`
   - `family-reminder-admin`
   - *(اختياري لاحقًا)* `family-reminder-app`
3. أضف ملفات البداية:
   - `README.md` يوضح الهدف وخطوات التشغيل السريعة.
   - `LICENSE` (اختَر MIT أو Apache 2.0).
   - `.gitignore` مناسب (Gradle/Spring للـ backend، Node/Vite للـ admin، KMP للـ app).
4. فعّل Branch Protection:
   - منع الدفع المباشر على `main`.
   - اشتراط PR + نجاح الفحوصات.

## 2. DNS Configuration
1. افتح لوحة تحكم الدومين `mohamedfaridelsherbini.com`.
2. تأكد من السجل الحالي:
   - `A` لـ `www` → ‎`161.35.20.4`‎.
3. أضف سجلات جديدة:
   - `api` نوع `A` → ‎`161.35.20.4`‎ (أو IP خاص بالـ backend إن توافر).
   - `admin` نوع `A` → ‎`161.35.20.4`‎.
4. استخدم `dig` أو `nslookup` للتحقق من انتشار الـ DNS.
   ```bash
   dig +short api.mohamedfaridelsherbini.com
   dig +short admin.mohamedfaridelsherbini.com
   ```

## 3. Server Preparation (DigitalOcean Droplet)
**المتطلبات:** Ubuntu 25.04 x64، مستخدم sudo.

1. تحديث النظام:
   ```bash
   sudo apt update && sudo apt upgrade -y
   sudo apt install nginx certbot python3-certbot-nginx git docker.io docker-compose-plugin -y
   sudo systemctl enable nginx docker
   ```
2. إنشاء مستخدم deploy (اختياري):
   ```bash
   sudo adduser deploy
   sudo usermod -aG sudo,docker deploy
   ```
3. إعداد جدار الحماية:
   ```bash
   sudo ufw allow OpenSSH
   sudo ufw allow 'Nginx Full'
   sudo ufw enable
   ```

## 4. Nginx Reverse Proxy
1. أنشئ ملفات الإعداد:
   ```bash
   sudo tee /etc/nginx/sites-available/personal-website <<'EOF'
   server {
     listen 80;
     server_name www.mohamedfaridelsherbini.com mohamedfaridelsherbini.com;

     location / {
       proxy_pass http://127.0.0.1:4000; # استبدل بالـ port الخاص بالموقع الشخصي
       proxy_set_header Host $host;
       proxy_set_header X-Real-IP $remote_addr;
     }
   }
   EOF

   sudo tee /etc/nginx/sites-available/family-reminder-api <<'EOF'
   server {
     listen 80;
     server_name api.mohamedfaridelsherbini.com;

     location / {
       proxy_pass http://127.0.0.1:8080;
       proxy_set_header Host $host;
       proxy_set_header X-Real-IP $remote_addr;
     }
   }
   EOF

   sudo tee /etc/nginx/sites-available/family-reminder-admin <<'EOF'
   server {
     listen 80;
     server_name admin.mohamedfaridelsherbini.com;

     location / {
       proxy_pass http://127.0.0.1:3000;
       proxy_set_header Host $host;
       proxy_set_header X-Real-IP $remote_addr;
     }
   }
   EOF
   ```
2. فعّل المواقع:
   ```bash
   sudo ln -s /etc/nginx/sites-available/personal-website /etc/nginx/sites-enabled/
   sudo ln -s /etc/nginx/sites-available/family-reminder-api /etc/nginx/sites-enabled/
   sudo ln -s /etc/nginx/sites-available/family-reminder-admin /etc/nginx/sites-enabled/
   sudo nginx -t
   sudo systemctl reload nginx
   ```
3. توفير الشهادات (HTTPS):
   ```bash
   sudo certbot --nginx -d mohamedfaridelsherbini.com -d www.mohamedfaridelsherbini.com
   sudo certbot --nginx -d api.mohamedfaridelsherbini.com
   sudo certbot --nginx -d admin.mohamedfaridelsherbini.com
   sudo systemctl status certbot.timer
   ```

## 5. Backend Deployment (Docker Compose)
1. استنسخ الريبو:
   ```bash
   git clone git@github.com:mohamedfaridelsherbini/family-reminder-backend.git
   cd family-reminder-backend
   ```
2. أنشئ ملف `.env` (راجع `docs/04-devops-platform.md`).
3. شغّل الخدمات محليًا:
   ```bash
   docker compose up -d
   ```
4. تأكد أن التطبيق يعمل:
   ```bash
   curl -I http://127.0.0.1:8080/actuator/health
   ```

## 6. Admin Frontend Deployment
1. استنسخ الريبو `family-reminder-admin`.
2. بناء الواجهة:
   ```bash
   npm install
   npm run build
   ```
3. شغّلها عبر Docker أو PM2/Nginx:
   ```bash
   npm run start # أو configure Dockerfile لنشر ثابت
   ```
4. حدّث إعداد Nginx ليتجه إلى البورت الصحيح (3000 أو خدمة Docker).

## 7. CI/CD Integration
1. Jenkins:
   - أضف Credentials (GitHub SSH، DigitalOcean API token إن استخدمته).
   - أنشئ Multibranch Pipeline لكل repo مرتبط بـ `Jenkinsfile`.
   - فعّل Webhooks على GitHub → Jenkins (`http://jenkins-server/github-webhook/`).
2. GitHub Actions (بديل):
   - أضف workflow `.github/workflows/ci.yml` لتشغيل build/test.
   - استخدم secrets `DOCKERHUB_USERNAME`, `DOCKERHUB_TOKEN`, `SSH_PRIVATE_KEY` للنشر.

## 8. Monitoring & Maintenance
- راقب الموارد عبر `htop`, `docker stats`, و DigitalOcean Insights.
- جدولة النسخ الاحتياطي Postgres (استعمل Managed DB أو `pg_dump` يومي).
- اختبر تجديد الشهادات (certbot) شهريًا.
- حدّث النظام والحزم بشكل دوري `sudo apt upgrade`.
- استكمل إعداد Grafana/Prometheus بناءً على خطة المراقبة في `docs/04-devops-platform.md`.
