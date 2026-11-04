# توقيع التطبيق (مرة واحدة)

الـ APK لازم يتوقّع بنفس المفتاح كل مرة، عشان الأندرويد يقبل التحديث فوق النسخة القديمة من غير ما يمسح بياناتها.
من غير المفتاح الـ workflow بيبني نسخة debug بمفتاح عشوائي (وبيطلّع تحذير في الـ log).

## 1) اعمل المفتاح (على كمبيوتر أو Termux على الموبايل)

    keytool -genkeypair -v -keystore release.jks -alias brightstar -keyalg RSA -keysize 2048 -validity 10000

اكتب باسورد واحفظه. **احتفظ بملف `release.jks` في مكان آمن (مش في GitHub)**؛ لو ضاع مش هتقدر تحدّث التطبيق تاني.

## 2) حوّله Base64

    base64 -w0 release.jks > release.b64        # Linux / Termux
    base64 -i release.jks -o release.b64         # macOS

## 3) ضيف 4 Secrets في GitHub
Repo → Settings → Secrets and variables → Actions → New repository secret

| الاسم | القيمة |
|---|---|
| `KEYSTORE_BASE64` | محتوى ملف `release.b64` |
| `KEYSTORE_PASSWORD` | باسورد الـ keystore |
| `KEY_ALIAS` | `brightstar` (أو الاسم اللي اخترته) |
| `KEY_PASSWORD` | باسورد المفتاح (لو نفس الباسورد سيبه فاضي) |

بعد كده كل build بيطلع `BrightStar.apk` موقّع، وبيطبع بصمة الشهادة (SHA-256) في الـ log؛ لازم تفضل ثابتة في كل مرة.

## أول تثبيت بعد التغيير
النسخة القديمة متوقّعة بمفتاح تاني، فلازم تتمسح قبل تثبيت الجديدة، وبياناتها (History والخطط والأرشيف) هتروح.
قبل ما تمسحها: ملف الأرشيف القديم (لو اتكتب) هيكون في `Documents/BrightStar/bs-archive.json` ويتفتح من زر Import في الأرشيف.
من النسخة الجديدة وطالع استخدم **History → Backup & restore** قبل أي تحديث أو مسح.

## تثبيت إصدارات المكتبات
أول build بيرفع ملف `package-lock.json` كـ artifact اسمه `package-lock`. نزّله وارفعه في الـ repo (جنب `package.json`)
وبعدها الـ workflow هيستخدم `npm ci` ويثبّت نفس الإصدارات في كل مرة.
