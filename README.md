# OnTime

**Everything on time.** An app for students and teachers: lesson schedule, assignments and alarms in one place.

## Who it is for
- **Students** - check the schedule, mark homework as done and never be late for class.
- **Teachers** - manage their teaching schedule, plan assignments for classes and track deadlines.

## Team
- Zarip Kanat - schedule, navigation, Room database setup, DataStore settings
- Bekzat Nurtaiuly - tasks, alarms, Room for tasks and alarms, REST API (Retrofit)

## Main features
- Weekly lesson schedule by day
- Lesson details with related assignments
- Assignment list with a "done" checkbox
- Adding new assignments with a deadline
- An alarm for every lesson that can be turned on and off

## Schedule (Zarip)
- **Schedule tab** - day chips Mo-Su, lessons of the selected day sorted by time. Today is selected when the app opens.
- **Lesson details** - type, day, time, room, group, note; edit and delete with a confirmation dialog.
- **Add / edit lesson** - one form for both modes, quick time slots, validation (name required, HH:MM time, start before end).
- Navigation routes `lesson/{lessonId}` and `lesson_edit?lessonId={lessonId}`.
- **Saved on the device** - lessons are stored in a Room database (`LessonEntity`, `LessonDao`, `AppDatabase`) and stay after the app is restarted.
- **Settings** - gear icon on the Schedule screen; the theme (Same as system / Light / Dark) is saved with DataStore.

## Tasks (assignments) (Bekzat)
- **Tasks tab** - all assignments with a "done" checkbox, filters (All / Active / Done), sorted by the nearest deadline. Overdue assignments are shown in red.
- **Add / edit assignment** - title, description, a related lesson and a deadline picked with a date picker. Assignments can be deleted.
- **Lesson details** - shows the assignments of that lesson, and a new assignment can be added with the lesson already selected.
- Navigation route `assignment_edit?assignmentId={id}&lessonId={id}` - both arguments are optional.

## Alarms (Bekzat)
- **Alarm tab** - every lesson has its own alarm, grouped by day, with the ring time and an on/off switch.
- **Next alarm** - a card at the top shows when the next alarm rings and how much time is left.
- **Alarm settings** - how many minutes before the lesson the alarm rings, on/off.
- When a lesson is deleted, its alarm and assignments are deleted too.

---

## Week 13: Requirements 8 and 9 (in progress)

### Requirement 9 - Room and DataStore

**Zarip**
- [x] Room, KSP and DataStore dependencies
- [x] `LessonEntity` (table `lessons`) and mappers `toLesson()` / `toEntity()`
- [x] `LessonDao` with CRUD: `@Insert`, `@Query`, `@Update`, `@Delete`
- [x] `AppDatabase` (`data/local/AppDatabase.kt`)
- [x] `OnTimeApplication` creates the database and initialises repositories
- [x] `LessonRepository` saves lessons in Room (same public functions, so other screens do not change)
- [x] DataStore: theme setting (System / Light / Dark) in `SettingsRepository`
- [x] Settings screen (gear icon on the Schedule screen) and theme applied in `MainActivity`

**Bekzat** (Zarip's Room code is already in `main` - you can start now)
- [x] `AssignmentEntity` + `AssignmentDao`
- [x] `LessonAlarmEntity` + `AlarmDao`
- [x] Add both entities to `AppDatabase` (`entities = [...]`, raise `version` by 1) and add the DAO functions
- [x] Initialise `AssignmentRepository` and `AlarmRepository` in `OnTimeApplication`, the same way as `LessonRepository.init(...)`
- [x] Move both repositories to Room with the same pattern as `LessonRepository` (keep the public functions)
- [x] Remove the sample assignments that point to lesson ids 1-3 (lessons now come from the database)

### Requirement 8 - REST API with Retrofit (Bekzat, can start now)
- API: Nager.Date public holidays (free, no key): `https://nagerholidays.com/api/v3/PublicHolidays/{year}/{countryCode}` (example: `https://nagerholidays.com/api/v3/PublicHolidays/2026/KZ` returns 14 holidays with `date`, `localName`, `name`)`
- [x] `INTERNET` permission in `AndroidManifest.xml`
- [x] Retrofit + JSON converter dependencies
- [x] `HolidayDto` data class, `HolidayApi` Retrofit interface with a `suspend` function
- [x] `HolidayRepository`
- [x] `HolidaysViewModel` with `StateFlow` and UI states **Loading / Success / Error / Empty**
- [x] `HolidaysScreen`: progress indicator, list, error message with a **Retry** button, empty message
- [x] Navigation route and an entry point (for example an icon on the Schedule screen or a fourth tab)

### Defense checklist
- [ ] Successful API request, loading and error with Retry (turn off the internet on the emulator)
- [ ] Room CRUD: add, open, edit, delete a lesson and an assignment
- [ ] Close and reopen the app: data and theme are still there
- [ ] Each of us explains our own code and shows our commits

---

## Tech stack
- Kotlin, Jetpack Compose, Material 3
- MVVM, ViewModel, StateFlow, Kotlin Coroutines
- Navigation Compose
- Room, DataStore (Week 13)
- Retrofit (Week 13)
