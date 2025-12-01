# Week 11 Lab 1: Studio Crit Demo Script

**Purpose**: Step-by-step script for presenting your work during studio critique

---

## Pre-Session Checklist (5 minutes before)

- [ ] Server running at http://127.0.0.1:8080
- [ ] Browser open with 2 tabs:
  - Tab 1: JavaScript enabled (for HTMX demos)
  - Tab 2: JavaScript disabled (for no-JS demos)
- [ ] Screen reader ready (NVDA/Narrator installed and tested)
- [ ] Presentation slides open (`studio-crit-slides.md` or exported PDF)
- [ ] Backup videos ready (in case live demo fails)
- [ ] Notebook for feedback notes
- [ ] Timer visible (15-minute total)

---

## Opening (30 seconds)

**Say**: 
> "Hi, I'm [your name]. Today I'm presenting the Task Manager inclusive redesign. I'll show how data-driven prioritization led to two critical accessibility fixes that improved completion rates from 57% to an expected 90%+. I'll demo live, show metrics, and prove WCAG compliance. Let's start."

**Do**:
- Make eye contact
- Speak clearly and at moderate pace
- Show Slide 1 (Title)

---

## Section 1: Problem Statement (1 minute)

**Say**:
> "Week 9 pilot testing with 5 participants revealed two Priority 1 issues in task editing. First, no-JS users couldn't complete edits. Participant P3's quote captures this: 'I clicked Save, but it went back to the list without changing... did it work?' Zero percent completion rate for no-JS—a clear WCAG 2.1.1 violation."

**Show**: Slide 2 with metrics and quote

**Say**:
> "Second, validation errors weren't announced to screen readers. 33% error rate, with participants P1 and P4 submitting blank titles. Screen reader users had no audio feedback. WCAG 3.3.1 violation."

**Pause**: Let audience absorb the problem

**Say**:
> "Both issues scored 8 out of 10 using Impact plus Inclusion minus Effort. High impact, high inclusion concern, low effort to fix. Clear Priority 1."

**Transition**: "Let's see how the fixes changed the metrics."

---

## Section 2: Metrics (3 minutes)

**Show**: Slide 3 (Before/After table)

**Say**:
> "Before fixes: 57% overall completion, zero percent no-JS. After—and I'll qualify this—expected 90% plus. Why 'expected'? Because I used mock data for Week 10 analysis. Real verification testing with participants is pending."

**Acknowledge limitation**:
> "This is a formative evaluation with sample size 5. Not statistically generalizable, but Nielsen's 5-user rule suggests we've found most issues."

**Highlight parity**:
> "The critical win is parity restoration. No-JS went from zero to expected 90%, closing a 67-percentage-point gap. This directly benefits users on assistive tech that strips JavaScript, users in reader mode, and users on low bandwidth."

**Visual cue**: Point to parity gap row in table

**Transition**: "Now let me show you how the fixes work."

---

## Section 3: Demo 1 - No-JS Edit Success (2 minutes)

**Setup**:
> "First demo: No-JS edit flow. I'm disabling JavaScript in DevTools."

**Do**:
1. Open DevTools (F12)
2. Settings → Disable JavaScript
3. Refresh page at http://127.0.0.1:8080/tasks

**Say while doing**:
> "JavaScript now off. I'll edit a task."

**Steps** (narrate each action):
1. **Click Edit** on "Draft report"
   > "Clicking Edit. Full page reload, slower—that's expected."
2. **Type new title**: "Submit report by Friday"
   > "Changing title to 'Submit report by Friday'."
3. **Click Save**
   > "Clicking Save. Watch what happens..."
4. **Page redirects**
   > "Page redirects to task list via POST-Redirect-GET pattern."
5. **Success message appears**
   > "Success message appears at top with green background: 'Task updated successfully.' Notice the blue focus outline—it received focus automatically."
6. **Tab away and back**
   > "I can Tab away... and Tab back. Message is keyboard-accessible."

**Compare before/after**:
> "Before: No message, no confirmation. P3 failed because they couldn't verify success. After: Clear feedback, focus guidance, 100% confidence."

**If demo fails**: Show backup video

---

## Section 4: Demo 2 - Screen Reader Error (2 minutes)

**Setup**:
> "Demo two: Validation error announcement. I'm turning on NVDA."

**Do**:
1. Enable JavaScript (for HTMX variant)
2. Start NVDA (Ctrl+Alt+N or from Start menu)
3. Navigate to task list

**Say**:
> "Screen reader is now active. You'll hear it announce elements as I navigate."

**Steps** (narrate + let SR speak):
1. **Click Edit** on a task
   > "Clicking Edit. Inline form appears."
2. **Clear title field**
   > "Clearing the title—making it blank."
3. **Press Enter**
   > "Pressing Enter to submit..."
4. **Listen for announcement**
   > [NVDA speaks: "Alert. Title is required. Please enter at least one character."]
   > "Did you hear that? 'Alert. Title is required.' Immediate announcement via role equals alert."

**Show code** (Slide 4 or DevTools):
> "Here's why: The error div has role equals alert and aria-live equals assertive. Screen readers hear it instantly."

**Compare**:
> "Before: Silent failure. After: Immediate announcement."

**Stop NVDA**: 
> "Turning off screen reader for next demo."

---

## Section 5: Demo 3 - Keyboard-Only (1 minute)

**Setup**:
> "Final demo: Keyboard-only workflow. No mouse from here."

**Do** (use only Tab, Enter, Shift+Tab):
1. **Tab to "Edit" button**
   > "Tabbing... Edit button has focus."
2. **Press Enter**
   > "Enter. Form appears."
3. **Tab to input**
   > "Tab to input field. Already focused? Good, that's autofocus."
4. **Type new text**
   > "Typing... 'Updated via keyboard only'."
5. **Press Enter to save**
   > "Enter to submit."
6. **Success message receives focus**
   > "Success message appears and receives focus. Blue outline visible."
7. **Tab back to list**
   > "Tab... Tab... now on task list. No keyboard traps."

**Say**:
> "All functionality available via keyboard. WCAG 2.1.1 compliance proven."

---

## Section 6: Accessibility Proof (2 minutes)

**Show**: Slide 6 (WCAG checklist)

**Say**:
> "Let's confirm WCAG 2.2 Level AA compliance. Criterion 2.1.1 Keyboard: Before, no-JS was broken. After, pass—you just saw it. 3.3.1 Error Identification: Before, not announced. After, pass—role equals alert works. 4.1.3 Status Messages: Before, fail—no success message. After, pass—role equals status announces politely."

**Show regression results**:
> "Regression testing: Keyboard nav, pass. Screen reader, pass. No-JS parity, pass. Visual rendering at 200% zoom, pass. All green."

**Visual cue**: Point to checkmarks in table

---

## Section 7: Evidence Chain (1 minute)

**Show**: Slide 7 (flowchart)

**Say**:
> "Evidence chain: Week 9 pilot data in metrics-mock CSV. Analyzed with Python script. Found T2 completion 57%, no-JS zero. Prioritized using Impact plus Inclusion framework. Implemented cookie-based status and role equals alert. Verified with regression testing. Expected result: 90% plus completion, WCAG AA compliant. Every claim traceable."

**Offer detail**:
> "Code changes visible in Git diff ae1308a to 93beb04. Analysis CSV and findings document in repo."

---

## Section 8: Limitations (1 minute)

**Show**: Slide 8

**Say**:
> "Let me acknowledge limitations. Small sample size, n equals 5. Formative evaluation, not generalizable. After-metrics are expected, not actual—real pilot testing pending. Cookie approach has 10-second expiry, might miss if user navigates away. Trade-off: simplicity versus robustness."

**Design trade-off**:
> "No-JS is slower—full page reload takes 3400 milliseconds versus 600 for HTMX. But WCAG requires parity, not identical performance. Functionality over speed."

**Be honest**:
> "Dual interaction confusion—Enter versus Save button—remains unresolved. Issue four, score one out of ten, deferred to future work."

---

## Section 9: Reflection (1 minute)

**Say**:
> "What went well: Data-driven prioritization made decisions clear. Small code changes, big impact—10 lines yielded 33-percentage-point improvement. Progressive enhancement works—HTMX and no-JS both functional. Evidence chain complete."

**What to improve**:
> "Need real participants for after-metrics. Should test more screen readers—only did NVDA, not JAWS or VoiceOver. Documentation could use more inline comments."

**Next steps**:
> "Semester 2 plans: Conduct verification testing. Fix remaining issues—required indicator for add task. Expand testing to mobile screen readers. Deploy to production and monitor real-world usage."

---

## Section 10: Q&A (5 minutes)

**Open floor**:
> "Questions? I'm interested in alternative approaches, missing tests, edge cases, or any gaps you noticed."

**While answering**:
- Take notes on all feedback (don't defend or explain excessively)
- Say "Great point, I'll add that to my backlog"
- If you don't know: "I don't have data on that—worth investigating"
- Thank critics: "Thank you, that's actionable"

**Common questions & prepared answers**:

**Q**: "Why cookies instead of server-side sessions?"  
**A**: "Simplicity—cookies work with stateless server design. Sessions would require session store. Trade-off acknowledged."

**Q**: "What if user blocks cookies?"  
**A**: "Good catch. Edge case not tested. Status messages wouldn't appear. Could fall back to URL parameters as backup. I'll document that."

**Q**: "When will you collect real data?"  
**A**: "After Semester 1 hand-in. Recruiting 2-3 participants for verification testing. Will update GitHub with results."

**Q**: "Did you test mobile screen readers?"  
**A**: "No—only NVDA on desktop. TalkBack and VoiceOver iOS are next. Limitation acknowledged in slides."

**Q**: "Why not fix Issue 3 (required indicator)?"  
**A**: "Priority 2, score four out of ten. Time-boxed to Priority 1 fixes. Issue 3 on backlog for Semester 2."

---

## Closing (30 seconds)

**Say**:
> "Thank you for your feedback. I've noted [number] actionable items to improve. Evidence pack available in GitHub repo, wk11 folder. Questions offline? Email or Slack me."

**Do**:
- Smile
- Thank audience
- Save your feedback notes
- Stop screen share

---

## Post-Session Debrief (5 minutes after)

**Self-reflection**:
- What went well? (e.g., demos worked, timing good)
- What went poorly? (e.g., rushed metrics section, forgot to zoom in)
- What feedback was most valuable? (e.g., cookie edge case)

**Action items**:
- Update backlog with critique points
- Fix any demo issues before next presentation
- Document limitations more clearly

**Update documents**:
- Add critique feedback to `wk11/lab-wk11/feedback-received.md`
- Revise slides if major gaps found
- Prepare for Week 11 Lab 2 (portfolio packaging)

---

**Script Length**: ~15 minutes  
**Timing Checkpoints**:
- 0:00 - Start
- 1:30 - Finish problem statement
- 4:30 - Finish metrics
- 9:30 - Finish all demos
- 11:30 - Finish accessibility proof
- 13:00 - Finish reflection
- 15:00 - End Q&A

**Remember**: 
- Breathe
- Speak clearly
- Show, don't just tell
- Acknowledge limitations honestly
- Welcome critique gracefully
