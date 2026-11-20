# OnTime

**Everything on time.** An app for students and teachers: lesson schedule, assignments and alarms in one place.

## Who it is for
- **Students** - check the schedule, mark homework as done and never be late for class.
- **Teachers** - manage their teaching schedule, plan assignments for classes and track deadlines.

## Team
- Zarip Kanat
- Bekzat Nurtaiuly

## Main features
- Weekly lesson schedule by day
- Lesson details with related assignments
- Assignment list with a "done" checkbox
- Adding new assignments with a deadline
- An alarm for every lesson that can be turned on and off

## Tasks (assignments)
- **Tasks tab** - all assignments with a "done" checkbox, filters (All / Active / Done), sorted by the nearest deadline. Overdue assignments are shown in red.
- **Add / edit assignment** - title, description, a related lesson and a deadline picked with a date picker. Assignments can be deleted.
- **Lesson details** - shows the assignments of that lesson, and a new assignment can be added with the lesson already selected.
- Navigation route `assignment_edit?assignmentId={id}&lessonId={id}` - both arguments are optional: `assignmentId` opens edit mode, `lessonId` pre-selects the lesson.

## Alarms
- **Alarm tab** - every lesson in the schedule has its own alarm. The list is grouped by day and shows the ring time, the lesson and an on/off switch.
- **Next alarm** - a card at the top shows when the next alarm rings and how much time is left.
- **Alarm settings** - choose how many minutes before the lesson the alarm rings (quick options 5 / 10 / 15 / 30 / 60 or - / + buttons) and turn it on or off. The ring time is shown before saving.
- Navigation route `alarm/{lessonId}` opens the settings of a lesson's alarm.
- When a lesson is deleted, its alarm and assignments are deleted too.

## Future plans
- Role selection: student or teacher
- Saving data on the device
- Deadline notifications and an alarm with sound

## Tech stack
- Kotlin, Jetpack Compose, Material 3
- MVVM, ViewModel, StateFlow
- Navigation Compose
