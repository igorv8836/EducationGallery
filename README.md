# Проектная работа для "Технология разработки программных приложений" - Android приложение "EducationGallery"
Приложение для сортировки фотографий согласно учебному расписанию, которое задается пользователем, либо номером группы для студентов МИРЭА.

## Участники приложения
* igorv8836 - разработка UI/UX
* DocZier - разработка интерфейс API и SQLite(Room)
* Voldemar-exe - разработка ETL (Extract, Transform, Load)

## Функционал
* Анализ всех фотографий из галереи
* Ввод расписания пользователем
* Получение расписания через API путем ввода группы, если студент МИРЭА
* Сортировка фотографий согласно расписанию (группировка фотографий отдельно по предмету, лекциям, практикам и номеру занятия)

## Скриншоты приложения
<img src="https://github.com/igorv8836/EducationGallery/assets/113043399/abf1910e-fe30-4645-b004-cc00ee7a5471" width="200" height="400" alt="screenshot">
<img src="https://github.com/igorv8836/EducationGallery/assets/113043399/a41334f2-82cb-4fba-be38-83638bcd54a6" width="200" height="400" alt="screenshot">
<img src="https://github.com/igorv8836/EducationGallery/assets/113043399/b3796cad-b1e3-46b4-bc11-131d39468d4d" width="200" height="400" alt="screenshot">
<img src="https://github.com/igorv8836/EducationGallery/assets/113043399/b25f75db-c9e2-49a1-b544-af64e420030f" width="200" height="400" alt="screenshot">
<img src="https://github.com/igorv8836/EducationGallery/assets/113043399/a70eac24-805b-4644-8679-bdd539b23980" width="200" height="400" alt="screenshot">
<img src="https://github.com/igorv8836/EducationGallery/assets/113043399/fcd791f1-b076-4f06-b8a6-fd03dd41a4be" width="200" height="400" alt="screenshot">
<img src="https://github.com/igorv8836/EducationGallery/assets/113043399/15824fa2-eae8-49e0-822d-aa69879841a4" width="200" height="400" alt="screenshot">
<img src="https://github.com/igorv8836/EducationGallery/assets/113043399/612e7975-b6a8-4910-b95f-745f871f37a7" width="200" height="400" alt="screenshot">
<img src="https://github.com/igorv8836/EducationGallery/assets/113043399/6d0e3669-1a05-4a4f-a799-b824ec95de72" width="200" height="400" alt="screenshot">


## Зависимости проекта
Зависимости проекта содержатся в директориях проекта в виде двух файлов: build.gradle.kts (Project level) и build.gradle.kts (Module level)

## Требования к самостоятельной сборке проекта
* Android Studio
* SDK > 24 (на Android 9, 10, 11, 14 проверен)

## Требования для работоспособности приложения
* SDK > 24
* Работающий сервис для получения расписания, либо ручной ввод расписания

## Используемые технологии
* Kotlin, Java
* Android API
* Room/SQLite
* MVVM
* Repository
* Flow and Coroutines
* Navigation
* Jetpack Compose
* Coil
