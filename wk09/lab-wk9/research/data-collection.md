# Week 9 Peer Pilot Data Collection Sheet

**Purpose**: Record quantitative data for each participant during pilot sessions. This complements the automated metrics.csv logging.

---

## Per-Task Data Collection

For **each participant**, record the following during their session:

### Participant: _____ (e.g., P1, P2, P3)
**Session ID**: ____________ (6-char hex from cookie)  
**Date**: YYYY-MM-DD  
**Test Variant**: ☐ Standard | ☐ Keyboard-only | ☐ Screen reader | ☐ No-JS  

---

### Task T3: Add New Task
**Scenario**: Add "Call supplier about delivery"  
**Time Limit**: 60 seconds

| Measure | Value |
|---------|-------|
| **Completion Status** | ☐ Success ☐ Failed ☐ Gave Up ☐ Abandoned |
| **Time on Task** (seconds) | _____ s |
| **Validation Errors** (count) | _____ |
| **Confidence Rating** (1-5) | _____ / 5 |
| **Notes** | |

---

### Task T1: Filter Tasks
**Scenario**: Filter by "report" and count matching tasks  
**Time Limit**: 120 seconds

| Measure | Value |
|---------|-------|
| **Completion Status** | ☐ Success ☐ Failed ☐ Gave Up ☐ Abandoned |
| **Time on Task** (seconds) | _____ s |
| **Correct Count?** | ☐ Yes (3) ☐ No (wrong count: _____) |
| **Validation Errors** (count) | _____ |
| **Confidence Rating** (1-5) | _____ / 5 |
| **Notes** | |

---

### Task T2: Edit Existing Task
**Scenario**: Change "Draft report" to "Submit report by Friday"  
**Time Limit**: 90 seconds

| Measure | Value |
|---------|-------|
| **Completion Status** | ☐ Success ☐ Failed ☐ Gave Up ☐ Abandoned |
| **Time on Task** (seconds) | _____ s |
| **Validation Errors** (count) | _____ |
| **Confidence Rating** (1-5) | _____ / 5 |
| **Notes** | |

---

### Task T4: Delete Task
**Scenario**: Delete "Test entry"  
**Time Limit**: 45 seconds

| Measure | Value |
|---------|-------|
| **Completion Status** | ☐ Success ☐ Failed ☐ Gave Up ☐ Abandoned |
| **Time on Task** (seconds) | _____ s |
| **Used Confirmation?** | ☐ Yes (modal) ☐ No (no-JS variant) |
| **Validation Errors** (count) | _____ |
| **Confidence Rating** (1-5) | _____ / 5 |
| **Notes** | |

---

## Post-Session Questionnaire

### UMUX-Lite (1-7 scale)

**Question 1**: "This system's capabilities meet my requirements."

☐ 1 - Strongly Disagree  
☐ 2  
☐ 3  
☐ 4 - Neutral  
☐ 5  
☐ 6  
☐ 7 - Strongly Agree  

**Question 2**: "This system is easy to use."

☐ 1 - Strongly Disagree  
☐ 2  
☐ 3  
☐ 4 - Neutral  
☐ 5  
☐ 6  
☐ 7 - Strongly Agree  

**UMUX-Lite Score**: _____ / 7 (average of Q1 and Q2)

---

### Optional: Overall Satisfaction

**Question**: "Overall, how satisfied are you with this task management interface?"

☐ 1 - Very Dissatisfied  
☐ 2  
☐ 3  
☐ 4 - Neutral  
☐ 5  
☐ 6  
☐ 7 - Very Satisfied  

---

## Accessibility Confirmations (if applicable)

For **Keyboard-only** sessions:
- [ ] All interactive elements were keyboard-accessible
- [ ] Focus indicators were visible and clear
- [ ] No keyboard traps encountered

For **Screen reader** sessions:
- [ ] Status updates were announced (ARIA live regions)
- [ ] Form errors were announced clearly
- [ ] Interactive elements had appropriate labels

For **No-JS** sessions:
- [ ] All tasks were completable without JavaScript
- [ ] PRG pattern prevented duplicate submissions
- [ ] Delete confirmation worked via POST fallback

---

## Session Summary

**Total Successes**: _____ / 4 tasks  
**Total Failures**: _____ / 4 tasks  
**Total Validation Errors**: _____  
**Average Confidence**: _____ / 5  
**UMUX-Lite Score**: _____ / 7  

**Facilitator's Overall Impression**:
- (e.g., Participant completed all tasks efficiently, seemed comfortable with interface)

---

**Data Recorded By**: ____________ (Facilitator initials)  
**Date**: YYYY-MM-DD  

---

# Aggregate Data Summary (After All Sessions)

Once all 5-6 sessions are complete, aggregate the data here:

## Completion Rates

| Task | Successes | Attempts | Completion Rate |
|------|-----------|----------|-----------------|
| T3 (Add) | _____ | _____ | _____ % |
| T1 (Filter) | _____ | _____ | _____ % |
| T2 (Edit) | _____ | _____ | _____ % |
| T4 (Delete) | _____ | _____ | _____ % |
| **Overall** | _____ | _____ | _____ % |

## Time-on-Task (seconds)

| Task | Times | Median | Mean | Std Dev | Range |
|------|-------|--------|------|---------|-------|
| T3 | ___, ___, ___, ___, ___ | _____ | _____ | _____ | _____ - _____ |
| T1 | ___, ___, ___, ___, ___ | _____ | _____ | _____ | _____ - _____ |
| T2 | ___, ___, ___, ___, ___ | _____ | _____ | _____ | _____ - _____ |
| T4 | ___, ___, ___, ___, ___ | _____ | _____ | _____ | _____ - _____ |

## Validation Error Rate

| Task | Total Errors | Total Attempts | Error Rate |
|------|--------------|----------------|------------|
| T3 (Add) | _____ | _____ | _____ % |
| T1 (Filter) | _____ | _____ | _____ % |
| T2 (Edit) | _____ | _____ | _____ % |
| T4 (Delete) | _____ | _____ | _____ % |

## Confidence Ratings (1-5)

| Task | Ratings | Mean | Std Dev |
|------|---------|------|---------|
| T3 | ___, ___, ___, ___, ___ | _____ | _____ |
| T1 | ___, ___, ___, ___, ___ | _____ | _____ |
| T2 | ___, ___, ___, ___, ___ | _____ | _____ |
| T4 | ___, ___, ___, ___, ___ | _____ | _____ |

## UMUX-Lite Scores (1-7)

| Participant | Q1 (Capabilities) | Q2 (Ease of Use) | Average |
|-------------|-------------------|------------------|---------|
| P1 | _____ | _____ | _____ |
| P2 | _____ | _____ | _____ |
| P3 | _____ | _____ | _____ |
| P4 | _____ | _____ | _____ |
| P5 | _____ | _____ | _____ |
| P6 | _____ | _____ | _____ |
| **Mean** | _____ | _____ | _____ |
| **Std Dev** | _____ | _____ | _____ |

---

**Analysis Completed**: YYYY-MM-DD  
**Analyst**: ____________  
