# Peer Pilot Protocol — Week 9

## Study Overview

**Purpose**: Evaluate usability and accessibility of task list prototype with peer participants.

**Type**: Low-risk formative evaluation, peer-to-peer within COMP2850 module.

**Scope**:
- 5–6 participants (lab pairs + self-testing)
- 4 tasks per session (~15–20 minutes total)
- No audio/video recording
- No personally identifiable information (PII) collected

**Ethical approval**: Covered by module's blanket low-risk consent for peer learning activities (verified with module leader).

**Data retention**: Anonymised logs stored in private repository for academic year, deleted after module assessment complete.

**References**:
- `../../../references/consent-pii-faq.md` — Privacy guidance
- `./tasks.md` — Full task definitions
- `./measures.md` — Metrics and calculations

---

## Participant Requirements

**Inclusion Criteria**:
- Enrolled in COMP2850
- Comfortable using web browsers
- Able to provide informed consent

**Exclusion Criteria**:
- None (module is inclusive by design)

**Accessibility Accommodations**:
- **Screen reader users**: Allowed 1.5× time limits, SR-specific observations recorded separately
- **Keyboard-only users**: Explicitly invited to test no-mouse variant
- **No-JS users**: At least one session conducted with JavaScript disabled
- **Magnification users**: Allowed extra time for navigation

**Expected Sample Size**: 5–6 participants (sufficient for formative evaluation, as per Nielsen: 5 users find 85% of usability issues)

---

## Consent Process

### Before Starting (Read Aloud)

> "Thanks for agreeing to pilot our task list prototype. This is a quick usability test—about 15 to 20 minutes. I'll ask you to complete 4 tasks while I observe and take notes.
>
> **Important**: I'm testing the interface, not you. There are no wrong answers. If something doesn't work or is confusing, that's valuable feedback about the design, not a reflection on you.
>
> **What we're collecting**:
> - Task completion times (from server logs, measured in milliseconds)
> - Whether you complete each task successfully (0 = fail, 1 = success)
> - Any validation errors or server issues encountered
> - Your confidence ratings after each task (on a scale of 1 to 5)
> - My notes on hesitations, accessibility issues, or interesting observations
>
> **What we're NOT collecting**:
> - Your name, email, student ID, or any personally identifiable information
> - Screen recordings, audio recordings, or video
> - Your device details beyond broad categories like 'keyboard-only' or 'screen reader'
> - Your keystrokes or typed content beyond what's necessary for task completion metrics
>
> **Privacy**:
> - I'll assign you a random anonymous session code (like `sid=X7kL9p`) which will appear in the logs
> - You can request that I delete all data linked to your session code at any time, even after today
> - Logs are stored locally, not synced to public repositories
> - Data will be deleted after module assessment is complete (end of academic year)
>
> **Your rights**:
> - **You can stop at any time**, no questions asked
> - **You can skip any task** if uncomfortable
> - **Participation/non-participation does not affect your grade** in any way
> - **You can request data deletion** by giving me your session code
>
> Do you have any questions before we start?"

### Consent Confirmation

**Verbal consent**: "Are you happy to proceed with these conditions?"

**If yes**: Record in `consent-log.md` (see template below)

**If no or hesitant**: "No problem at all, thanks for considering it. Would you like to observe instead?"

**Consent Log Template** (`wk09/lab-wk9/research/consent-log.md`):
```
Date: 2025-12-01
Participant code: P1
Session ID: 7a9f2c
Consent: Verbal consent given
Variant: [Standard / Keyboard-only / Screen reader / No-JS]
Notes: [Any special accommodations requested]
```

### Opt-Out / Data Deletion Path

**If participant requests deletion** (during or after session):

1. Open `data/metrics.csv`
2. Delete all rows where `session_id=<their session ID>`
3. Delete corresponding entry in `pilot-notes.md`
4. Update `consent-log.md`: Add line "Data deleted on request [date]"
5. Confirm to participant: "Your data has been deleted"

---

## Session Setup

### Environment

**Location**: Quiet space in lab (not open-plan area to avoid distractions/overhearing)

**Equipment**:
- **Participant**: Uses their own laptop/desktop with browser open to prototype
- **Facilitator**: Separate laptop for notes (don't share screen—distracting)

**Physical Setup**:
- Facilitator sits to the side (not directly behind—feels invasive)
- Participant should feel like they're working independently, not being watched

### Pre-Pilot Checklist

1. **Generate random session ID**:
   - PowerShell: `([guid]::NewGuid().ToString() -split '-')[0]` → e.g., `7a9f2c`
   - Or online tool: https://www.random.org/strings/ (6 chars, hex)

2. **Set session cookie in participant browser**:
   - Open browser DevTools → Console
   - Run: `document.cookie = "sid=7a9f2c; path=/";`
   - Verify: `document.cookie` should show `sid=7a9f2c`

3. **Prepare prototype**:
   - Navigate to `http://localhost:8080/tasks` (or deployment URL)
   - Verify server is running and logging enabled
   - Seed task list with test data (see `tasks.md` Setup sections)
   - For T1: Ensure 3 tasks contain "report", 7 others don't
   - For T2: Ensure "Draft report" task exists
   - For T4: Ensure "Test entry" task exists

4. **Open facilitator materials**:
   - `pilot-notes.md` template (one per participant)
   - Task scenarios printed or on separate screen
   - Stopwatch ready (backup timing if server logs fail)

5. **Verify logging**:
   - Make a test request (add a task)
   - Check `data/metrics.csv` updates with new row
   - Confirm columns: `ts_iso`, `session_id`, `request_id`, `task_code`, `step`, `outcome`, `ms`, `http_status`, `js_mode`

---

## Session Flow

### 0. Introduction (2 minutes)

- **Welcome**: Greet participant, thank them for their time
- **Consent process**: Read consent script (see above), get verbal confirmation
- **Explain think-aloud (optional)**: "Feel free to say what you're thinking as you work, but no pressure. Some people find it helpful to narrate, others prefer silence—either is fine."
- **Set expectations**: "I won't help or give hints unless you're completely stuck for more than 3 minutes. Silence from me doesn't mean you're doing it wrong—I'm just observing."

### 1. Warm-up (2 minutes, not timed)

**Say**:
> "Take a minute or two to browse the task list. Click around, get familiar with the interface. Try adding a task, filtering, anything you're curious about. This isn't part of the test, just to get comfortable. Let me know when you're ready to start the timed tasks."

**Observe** (don't record, but note mentally):
- Do they immediately understand it's a task list?
- Do they try features unprompted (good sign of affordances)?
- Any early confusion?

### 2. Task T3: Add Task (60 seconds time limit)

**Read scenario** (or hand printed sheet):
> "You need to remember to 'Call supplier about delivery'. Add this as a new task to your list."

**Start timing**: When participant focuses in input field (if manual timing) or when they start (server logs automatically)

**Observe** (note in `pilot-notes.md`):
- ✓ Do they find the form immediately?
- ✗ Do they submit blank by mistake (validation error)?
- ✓ Do they notice the success confirmation message?
- ✓ Does the new task appear in the list?

**If participant exceeds 60s**: Prompt "Would you like to continue, or move to the next task?"

**Post-task question**:
> "On a scale of 1 to 5, how confident are you that you completed that task correctly? 1 is not confident at all, 5 is very confident."

**Record**:
- Completion: 0 (fail), 0.5 (partial), or 1 (success)
- Confidence: 1–5
- Facilitator notes: Observations, quotes, timestamps

---

### 3. Task T1: Filter Tasks (120 seconds time limit)

**Read scenario**:
> "You've been asked to find all tasks containing the word 'report'. Use the filter box to show only matching tasks, then count how many tasks remain and tell me the number."

**Observe**:
- ✓ Do they find the filter box immediately?
- ✓ Do they type "report" and wait for results?
- ✓ Do they notice the result count message ("Showing 3 of 10 tasks")?
- ✗ Do they manually count items instead of reading the count?
- ✓ Do they report the correct count (3)?

**Accessibility check** (if SR user):
- Does screen reader announce result count?

**Post-task**: Confidence rating (1–5)

---

### 4. Task T2: Edit Task (90 seconds time limit)

**Read scenario**:
> "The task 'Draft report' needs to be updated. Change the title to 'Submit report by Friday' and save the change."

**Observe**:
- ✓ Do they find the Edit button?
- ✓ Do they click Edit and see the edit form appear?
- ✗ Do they trigger validation errors (e.g., submit blank)?
- ✓ If error occurs, do they recover successfully?
- ✓ Do they verify the change persisted (updated title visible)?

**Accessibility check**:
- Does screen reader announce "Updated [title]" after save?
- Does focus remain on/near the edited task?

**Post-task**: Confidence rating (1–5)

---

### 5. Task T4: Delete Task (45 seconds time limit)

**Read scenario**:
> "The task 'Test entry' is no longer needed. Delete it from the list."

**Observe**:
- ✓ Do they find the Delete button?
- ✓ **HTMX path (JS-on)**: Do they see confirmation dialog? Do they confirm?
- ✓ **No-JS path**: Form submits immediately (no confirmation)
- ✓ Do they verify the task disappeared from the list?

**Note**: Document whether JS-on or JS-off variant was used

**Post-task**: Confidence rating (1–5)

---

### 6. Debrief (3 minutes)

**Ask open-ended questions**:

1. "Which task felt most difficult or confusing?"
2. "Did anything surprise you or not work as you expected?"
3. "Were there any points where you weren't sure if something had worked?"
4. "(For SR/keyboard users) Did you encounter any accessibility barriers?"
5. "How does this interface compare to other task managers you've used?"

**Record verbatim quotes** in notes (especially negative feedback—most actionable)

**Thank participant**:
> "That's really helpful, thank you. Your feedback will directly improve the prototype. If you think of anything else later, you can message me on Teams."

**Remind about data deletion**:
> "Remember, if you'd like me to delete your data, just let me know your session code [show them: `7a9f2c`]."

---

## Optional: Post-Session UMUX-Lite (if time permits)

**Ask** (7-point scale: 1=strongly disagree, 7=strongly agree):

1. "This system's capabilities meet my requirements."
2. "This system is easy to use."

**Record** responses. Calculate UMUX-Lite score = average of Q1 and Q2.

**Note**: Only if session isn't already over time. Priority is task observations.

---

## Facilitator Guidelines

### Do:

- ✓ Remain **neutral** (don't lead: "Did you see the status message?" → reveals expected behavior)
- ✓ Take **detailed notes** (timestamps, direct quotes, non-verbal cues like hesitation)
- ✓ Allow **silence** (don't fill pauses—participant might be thinking)
- ✓ Note **when you intervene** ("Prompted after 3min stuck: asked 'What are you looking for?'")
- ✓ Be **encouraging** without being leading ("Thanks, that's helpful")

### Don't:

- ✗ **Explain the interface** before tasks (defeats the purpose of usability testing)
- ✗ **Show participant how to do something** (if they ask, respond: "What do you think would work?")
- ✗ **Justify design choices** ("It's supposed to work like this..." → defensive, dismisses feedback)
- ✗ **Make participant feel judged** (avoid: "Most people find that easily")
- ✗ **Interrupt think-aloud** to correct misconceptions (note it, fix in redesign)

### If Participant is Completely Stuck:

1. **Wait 3 minutes** (check stopwatch)
2. **Ask diagnostic question**: "What are you looking for?" or "What do you expect to happen?"
3. **If still stuck after another minute**: "Let's move to the next task—this feedback about difficulty is valuable."
4. **Mark task as failed**, note reason in observations ("Couldn't find Edit button, searched for 3min")

---

## Data Recording

### Automated (Server Logs → `data/metrics.csv`)

Columns:
- `ts_iso`: Timestamp (ISO 8601 format)
- `session_id`: Anonymous token (e.g., `7a9f2c`)
- `request_id`: Unique request identifier
- `task_code`: T1_filter, T2_edit, T3_add, T4_delete
- `step`: start, success, validation_error, server_error
- `outcome`: Error details if step=validation_error (e.g., "blank_title")
- `ms`: Duration in milliseconds
- `http_status`: 200, 400, 500, etc.
- `js_mode`: on, off, unknown

**Logged automatically** when routes execute (see instrumentation implementation)

### Manual (`wk09/lab-wk9/research/pilot-notes.md`)

Template per participant:

```markdown
## Session P1

**Date**: 2025-12-01  
**Session ID**: 7a9f2c  
**Variant**: Standard (JS-on, mouse+keyboard)  
**Start time**: 14:20  

### Task T3: Add Task

**Start**: 14:22  
**End**: 14:23  
**Duration**: 47s  
**Completion**: 1 (success)  
**Confidence**: 4/5  

**Observations**:
- [14:22:15] Participant hesitated before clicking "Add Task" button—looked around as if expecting another step
- [14:22:45] Success message appeared, participant said "Oh okay, it worked"
- [14:23:00] Verified task in list—good practice

**Quote**: "I wasn't sure if Enter would work or if I had to click the button"

**Tags**: #ux-feedback #button-affordance

---

### Task T1: Filter Tasks

**Start**: 14:24  
**End**: 14:25  
**Duration**: 58s  
**Completion**: 1 (success)  
**Confidence**: 5/5  

**Observations**:
- [14:24:10] Typed "report" slowly, watching for instant results—Active Search worked as expected
- [14:24:30] Read result count message aloud: "Showing 3 of 10 tasks"
- [14:24:50] Reported correct count (3)

**Quote**: "I like that it filters as I type, didn't need to click a button"

**Tags**: #ux-positive #active-search

---

[Continue for T2, T4...]

### Debrief Notes

**Most difficult task**: T2 (Edit) — "Wasn't sure if the change saved, had to scroll to check"

**Surprises**: "Expected a 'Save successful' popup, not just text at the top"

**Accessibility (N/A—visual user)**: No issues reported

**Comparison to other tools**: "Similar to Todoist, but simpler"

**Additional feedback**: "Would be nice to have a way to undo deletes"

---

### Summary

**Overall impression**: Positive, a few feedback clarity issues  
**Critical issues**: Status message placement not noticed initially (T2, T3)  
**UMUX-Lite**: Q1=6, Q2=5 → Score=5.5 (Good)  
**Recommended fixes**: Make status messages more prominent, add undo feature

```

---

## Post-Session Cleanup

After each participant:

1. **Export metrics.csv to backup**:
   - Copy `data/metrics.csv` to `wk09/lab-wk9/data/metrics-backup-[date].csv`
   - Ensures data not lost if file gets corrupted

2. **Reset task list to default state**:
   - Remove test data added during session
   - Re-seed with standard tasks for next participant

3. **Review facilitator notes**:
   - Fill in any gaps while memory is fresh
   - Clarify abbreviations or unclear shorthand

4. **Transfer confidence ratings**:
   - Add to summary spreadsheet or analysis document

5. **Check server logs**:
   - Verify all tasks logged correctly
   - Note any 500 errors (bugs requiring fix before next session)

---

## Variants & Special Conditions

### Keyboard-Only Testing

**Modifications**:
- Ask participant to avoid mouse entirely
- Allow 1.2× time limits
- Focus observations on Tab order, visible focus indicators, Enter/Escape functionality

**Extra checks**:
- Can all interactive elements be reached via Tab?
- Is focus visible on all elements?
- Do shortcuts work (Enter to submit, Escape to cancel)?

### Screen Reader Testing

**Modifications**:
- Allow 1.5× time limits
- Note which screen reader (NVDA, JAWS, VoiceOver, Narrator)
- Focus observations on ARIA announcements, semantic HTML

**Extra checks**:
- Are status messages announced (`aria-live` regions)?
- Are error messages associated with inputs (`aria-describedby`)?
- Are interactive controls properly labeled (`aria-label`)?

### No-JS Testing

**Modifications**:
- Disable JavaScript in browser: DevTools → Settings → Disable JavaScript
- Refresh page (`Ctrl+Shift+R`)
- Note full page reloads vs HTMX fragment swaps

**Extra checks**:
- Do all features work (add, edit, delete, filter)?
- Are form submissions handled via PRG (Post-Redirect-Get)?
- Are validation errors displayed in no-JS mode (query params)?

**Expected behavior differences**:
- T4 (Delete): No confirmation dialog in no-JS mode (documented trade-off)
- T1 (Filter): Must click "Apply Filter" button (no Active Search)
- T2 (Edit): Page reloads after save (not in-place swap)

---

## Troubleshooting

### Server Logs Not Appearing

**Check**:
1. Is server running? (`gradlew run`)
2. Is logging enabled in code? (see instrumentation section)
3. Does `data/metrics.csv` file exist with correct permissions?

**Fallback**: Use facilitator stopwatch timing exclusively

### Participant Can't See Status Messages

**Note** in observations: "Status message visibility issue—participant didn't notice updates"

**Don't** point it out during session (that's the usability issue we're testing for!)

### Session Running Over Time

**If approaching 25 minutes**:
- Skip remaining tasks: "We're running short on time, so let's move to the debrief"
- Still collect confidence ratings for completed tasks
- Note in records: "Tasks T3, T4 skipped due to time"

### Participant Encounters a Bug

**Do**:
- Note exact steps to reproduce
- Mark task as system error (not participant failure)
- Decide whether to skip task or retry after refresh

**Example**: "Server returned 500 error on save—bug in validation logic. Task T2 marked as system error, not counted in completion rate."

---

## Ethics & Privacy Checklist

Before first session:

- [ ] Read `../../../references/consent-pii-faq.md`
- [ ] Verify `data/metrics.csv` is in `.gitignore` (not synced to public GitHub)
- [ ] Confirm no PII fields in logs (no names, emails, IPs)
- [ ] Prepare consent script
- [ ] Set up opt-out procedure (know how to delete session data)

During each session:

- [ ] Obtain verbal consent
- [ ] Record consent in `consent-log.md`
- [ ] Remind participant they can stop anytime
- [ ] Answer questions about data collection honestly

After all sessions:

- [ ] Anonymize any remaining identifiers (double-check no names in notes)
- [ ] Store data securely (private repo, not shared drives)
- [ ] Plan data deletion after module assessment (end of academic year)

---

## Summary

**Total time per session**: 15–20 minutes
- Introduction + consent: 2 min
- Warm-up: 2 min
- 4 tasks: 8–10 min (including confidence ratings)
- Debrief: 3 min
- Buffer: 1–2 min

**Data collected**:
- Objective: completion rates, times, error counts, HTTP status codes
- Subjective: confidence ratings, UMUX-Lite (optional), qualitative feedback

**Ethical foundation**:
- Informed consent, opt-out honored, no PII, data minimization

**Next steps** (Week 10):
- Analyze metrics.csv (median times, completion rates)
- Thematic analysis of facilitator notes
- Prioritize fixes based on frequency + severity
- Redesign and reverify

