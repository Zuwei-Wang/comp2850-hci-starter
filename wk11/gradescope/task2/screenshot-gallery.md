# Week 11 Lab 2: Screenshot Evidence Gallery

**Purpose**: Visual evidence for Gradescope Task 2 submission  
**Status**: Template — awaiting user to capture screenshots during verification testing  

---

## Instructions

For each placeholder below, capture a screenshot that clearly shows:
1. **Context**: URL, browser info, date/time visible
2. **Focus**: The feature being tested (success message, error, etc.)
3. **Proof**: Visual indicators (focus outline, NVDA speech viewer, etc.)

**Screenshot requirements**:
- **Resolution**: Minimum 1280×720 (HD), preferred 1920×1080 (Full HD)
- **Format**: PNG (lossless) or JPEG at 90%+ quality
- **File size**: < 2 MB per image (compress with https://tinypng.com/ if needed)
- **File naming**: Descriptive, no spaces (e.g., `P6_T2_nojs_success.png`)
- **Annotations**: Use red arrows/boxes to highlight key elements (optional but recommended)

---

## Category 1: No-JS Success Message

### Screenshot 1.1: No-JS Edit Success (Full Page)

**File name**: `P6_T2_nojs_success_full.png`  
**Scenario**: Participant P6, Task T2 (edit task), no-JS variant, success case  

**What to capture**:
- [ ] Browser with JavaScript disabled (visible in DevTools or address bar indicator)
- [ ] Task list page at http://127.0.0.1:8080/tasks
- [ ] Green success message at top: "✓ Task '[task title]' updated successfully."
- [ ] Blue focus outline around success message (proving auto-focus worked)
- [ ] Task list showing updated task title

**Step-by-step**:
1. Open Chrome/Firefox
2. Open DevTools (F12) → Settings → Disable JavaScript
3. Refresh page at http://127.0.0.1:8080/tasks
4. Click "Edit" on a task (e.g., "Draft report")
5. Change title to "Submit report by Friday"
6. Click "Save"
7. Page redirects to task list with success message
8. **Capture screenshot NOW** (before 10-second cookie expires)
9. Save as `P6_T2_nojs_success_full.png`

**Annotation tips**:
- Red arrow → success message
- Red box → blue focus outline
- Red circle → updated task in list

---

### Screenshot 1.2: No-JS Success Message (Close-up)

**File name**: `P6_T2_nojs_success_closeup.png`  
**Scenario**: Same as 1.1, zoomed in on success message  

**What to capture**:
- [ ] Success message div with role="status"
- [ ] Blue focus outline (2px solid)
- [ ] Green background (#d4edda) and dark green text (#155724)
- [ ] Checkmark icon (✓)
- [ ] Task title shown in message

**Step-by-step**:
1. Use same session as Screenshot 1.1
2. Zoom to 200% or use browser zoom (Ctrl + Plus)
3. Crop/focus on success message only
4. **Capture screenshot**
5. Save as `P6_T2_nojs_success_closeup.png`

---

### Screenshot 1.3: Chrome DevTools Showing Cookie

**File name**: `P6_T2_cookie_devtools.png`  
**Scenario**: Proof that edit_success cookie was set and read  

**What to capture**:
- [ ] Chrome DevTools → Application tab → Cookies → http://127.0.0.1:8080
- [ ] `edit_success` cookie with value "1:Submit report by Friday"
- [ ] maxAge = 10 seconds (or expiry timestamp)
- [ ] Path = /
- [ ] HttpOnly = false (cookies must be readable by client for proof)

**Step-by-step**:
1. Use same session as Screenshot 1.1
2. Open DevTools (F12) → Application tab
3. Expand Cookies → http://127.0.0.1:8080
4. **Capture screenshot QUICKLY** (cookie expires in 10s)
5. If missed, re-do edit and capture immediately after redirect
6. Save as `P6_T2_cookie_devtools.png`

---

## Category 2: No-JS Validation Error

### Screenshot 2.1: No-JS Validation Error (Full Page)

**File name**: `P7_T2_nojs_error_full.png`  
**Scenario**: Participant P7, Task T2 (edit task), no-JS variant, validation error  

**What to capture**:
- [ ] Browser with JavaScript disabled
- [ ] Task list page with inline edit form open
- [ ] Red error message below input: "Title is required. Please enter at least one character."
- [ ] Blue focus outline around error message (proving auto-focus worked)
- [ ] Input field with aria-invalid="true" (inspect element to show)

**Step-by-step**:
1. Open Chrome/Firefox with JavaScript disabled
2. Navigate to http://127.0.0.1:8080/tasks
3. Click "Edit" on a task
4. **Clear the title field** (make it blank)
5. Click "Save" or press Enter
6. Page redirects back to edit form with error message
7. **Capture screenshot NOW**
8. Save as `P7_T2_nojs_error_full.png`

**Annotation tips**:
- Red arrow → error message
- Red box → blue focus outline
- Red circle → blank input field

---

### Screenshot 2.2: No-JS Error Message (Close-up)

**File name**: `P7_T2_nojs_error_closeup.png`  
**Scenario**: Same as 2.1, zoomed in on error message  

**What to capture**:
- [ ] Error div with role="alert" (inspect element to show)
- [ ] Red text color (#d32f2f)
- [ ] Blue focus outline
- [ ] Error text: "Title is required..."

**Step-by-step**:
1. Use same session as Screenshot 2.1
2. Right-click error message → Inspect
3. Expand div element to show attributes:
   ```html
   <div id="error-1" role="alert" aria-live="assertive" tabindex="-1" style="color: #d32f2f;">
     Title is required. Please enter at least one character.
   </div>
   ```
4. Zoom to 200%
5. **Capture screenshot showing both error message and DevTools**
6. Save as `P7_T2_nojs_error_closeup.png`

---

### Screenshot 2.3: ARIA Attributes Inspection

**File name**: `P7_T2_error_aria_inspect.png`  
**Scenario**: Proof of ARIA attributes on error div  

**What to capture**:
- [ ] Chrome DevTools → Elements tab
- [ ] Error div HTML highlighted:
   - `id="error-1"`
   - `role="alert"`
   - `aria-live="assertive"`
   - `tabindex="-1"`
- [ ] Accessibility tree (DevTools → Accessibility tab) showing "alert" role

**Step-by-step**:
1. Use same session as Screenshot 2.1
2. Inspect error message
3. DevTools → Elements tab → expand error div
4. Optionally: Switch to Accessibility tab to show computed role
5. **Capture screenshot**
6. Save as `P7_T2_error_aria_inspect.png`

---

## Category 3: Screen Reader Announcements

### Screenshot 3.1: NVDA Speech Viewer (Success Message)

**File name**: `P8_T2_nvda_success_speech.png`  
**Scenario**: Participant P8, Task T2 (edit task), NVDA screen reader, success case  

**What to capture**:
- [ ] NVDA Speech Viewer window showing: "Task 'Submit report by Friday' updated successfully."
- [ ] Timestamp showing immediate announcement
- [ ] Task list page in background

**Step-by-step**:
1. Install NVDA from https://www.nvaccess.org/ (free)
2. Start NVDA (Ctrl+Alt+N)
3. Open NVDA menu (Insert+N) → Tools → Speech Viewer
4. Navigate to http://127.0.0.1:8080/tasks (JavaScript ON for HTMX variant)
5. Edit a task successfully
6. **Capture screenshot of Speech Viewer**
7. Save as `P8_T2_nvda_success_speech.png`

**Alternative**: If Speech Viewer not available, record video and extract frame

---

### Screenshot 3.2: NVDA Speech Viewer (Error Announcement)

**File name**: `P8_T2_nvda_error_speech.png`  
**Scenario**: Same as 3.1, but validation error case  

**What to capture**:
- [ ] NVDA Speech Viewer showing: "Alert. Title is required. Please enter at least one character."
- [ ] "Alert" keyword proving role="alert" worked
- [ ] Timestamp showing immediate announcement (assertive)

**Step-by-step**:
1. Use same NVDA session as Screenshot 3.1
2. Edit a task, clear title field, press Enter
3. NVDA announces error immediately
4. **Capture screenshot of Speech Viewer**
5. Save as `P8_T2_nvda_error_speech.png`

**Note**: NVDA Speech Viewer shows text announcement only. For audio proof, record video with system audio.

---

### Screenshot 3.3: NVDA Elements List (ARIA Regions)

**File name**: `P8_T2_nvda_elements_list.png`  
**Scenario**: Proof that ARIA live regions are recognized by NVDA  

**What to capture**:
- [ ] NVDA Elements List (Insert+F7) showing landmarks/regions
- [ ] "status" region (from role="status")
- [ ] "alert" region (from role="alert")

**Step-by-step**:
1. Use same NVDA session
2. Press Insert+F7 to open Elements List
3. Switch to "Landmarks" or "Regions" tab
4. Look for "status" or "alert" entries
5. **Capture screenshot**
6. Save as `P8_T2_nvda_elements_list.png`

**Note**: Not all ARIA live regions appear in Elements List; Speech Viewer (3.1, 3.2) is primary evidence.

---

## Category 4: Keyboard Navigation

### Screenshot 4.1: Tab Focus Sequence

**File name**: `P9_T2_keyboard_tab_sequence.png`  
**Scenario**: Participant P9, Task T2, keyboard-only navigation  

**What to capture**:
- [ ] Task list with multiple tasks
- [ ] Blue focus outline on "Edit" button
- [ ] Other focusable elements (input, Save button, success message) numbered with annotations

**Step-by-step**:
1. Open http://127.0.0.1:8080/tasks
2. Press Tab repeatedly to move through:
   - Task 1 "Edit" button
   - Task 2 "Edit" button
   - ...
3. Click "Edit" on one task
4. Press Tab to move through edit form:
   - Input field
   - Save button
5. Submit form (Enter or click Save)
6. Success message receives focus
7. **Capture screenshot at each step** (or annotate single screenshot)
8. Save as `P9_T2_keyboard_tab_sequence.png`

**Annotation tips**: Number each focusable element (1, 2, 3...) to show sequence

---

### Screenshot 4.2: Focus Outline on Success Message

**File name**: `P9_T2_keyboard_success_focus.png`  
**Scenario**: Proof that success message receives focus after redirect  

**What to capture**:
- [ ] Success message at top of page
- [ ] Blue focus outline (2px solid, clearly visible)
- [ ] Keyboard icon or visual cue that Tab key was pressed (optional)

**Step-by-step**:
1. Use same session as Screenshot 4.1
2. After edit success, success message should have focus automatically
3. Zoom to 200% to show focus outline clearly
4. **Capture screenshot**
5. Save as `P9_T2_keyboard_success_focus.png`

---

### Screenshot 4.3: Focus Outline on Error Message

**File name**: `P9_T2_keyboard_error_focus.png`  
**Scenario**: Proof that error message receives focus after validation failure  

**What to capture**:
- [ ] Error message below input field
- [ ] Blue focus outline on error div
- [ ] Input field with aria-invalid="true" (inspect element)

**Step-by-step**:
1. Edit a task, clear title, press Enter
2. Error message appears with focus
3. Zoom to 200%
4. **Capture screenshot**
5. Save as `P9_T2_keyboard_error_focus.png`

---

## Category 5: Regression Testing (Existing Features)

### Screenshot 5.1: HTMX Inline Edit (No Page Reload)

**File name**: `P10_T2_htmx_inline_edit.png`  
**Scenario**: Proof that HTMX variant still works (Priority 1 fixes didn't break it)  

**What to capture**:
- [ ] Task list with inline edit form open
- [ ] Network tab in DevTools showing HTMX request (hx-request: true header)
- [ ] No full page reload (URL doesn't change)

**Step-by-step**:
1. Open http://127.0.0.1:8080/tasks with JavaScript ENABLED
2. Open DevTools → Network tab
3. Click "Edit" on a task
4. Change title, press Enter
5. Observe: Inline swap (no page reload)
6. **Capture screenshot showing Network tab with /tasks/{id} POST request**
7. Save as `P10_T2_htmx_inline_edit.png`

---

### Screenshot 5.2: Visual Rendering at 200% Zoom

**File name**: `P10_T2_visual_200zoom.png`  
**Scenario**: WCAG 1.4.4 (Resize Text) — ensure no overflow at 200% zoom  

**What to capture**:
- [ ] Task list at 200% zoom (Ctrl + Plus)
- [ ] Success message fully visible (no text cutoff)
- [ ] Error message fully visible
- [ ] No horizontal scroll required

**Step-by-step**:
1. Open http://127.0.0.1:8080/tasks
2. Zoom to 200% (browser zoom or OS accessibility settings)
3. Trigger success message (edit a task)
4. **Capture screenshot**
5. Zoom back to 100%, trigger error message
6. Zoom to 200% again
7. **Capture second screenshot**
8. Save as `P10_T2_visual_200zoom_success.png` and `P10_T2_visual_200zoom_error.png`

---

## Category 6: Comparison (Before vs After)

### Screenshot 6.1: Before Fix (No Success Message)

**File name**: `before_nojs_no_success_message.png`  
**Scenario**: Historical evidence showing problem state (Week 9)  

**What to capture**:
- [ ] Task list page after no-JS edit (before Week 10 fixes)
- [ ] No success message visible
- [ ] User has no confirmation that edit worked

**How to obtain**:
- **Option A**: Git checkout to commit before ae1308a, run server, capture screenshot
- **Option B**: Mock screenshot using image editor (less authentic)
- **Option C**: Use pilot testing screenshots from Week 9 (if available)

**Command**:
```powershell
git checkout ae1308a~1
./gradlew run
# Capture screenshot
git checkout main
```

---

### Screenshot 6.2: After Fix (Success Message Visible)

**File name**: `after_nojs_success_message.png`  
**Scenario**: Evidence showing solution state (Week 10)  

**What to capture**:
- [ ] Same scenario as 6.1, but with Week 10 fixes applied
- [ ] Green success message at top
- [ ] Focus outline visible
- [ ] Clear visual contrast between 6.1 and 6.2

**Step-by-step**:
1. Ensure server running on main branch (Week 10 fixes)
2. Repeat exact same steps as Screenshot 6.1
3. **Capture screenshot**
4. Save as `after_nojs_success_message.png`

**Presentation tip**: Place 6.1 and 6.2 side-by-side in executive summary

---

## Category 7: Lighthouse & axe DevTools Reports

### Screenshot 7.1: Lighthouse Accessibility Score

**File name**: `lighthouse_accessibility_score.png`  
**Scenario**: Automated accessibility audit (WCAG compliance)  

**What to capture**:
- [ ] Chrome DevTools → Lighthouse tab
- [ ] Accessibility score = 100 (or explanation if lower)
- [ ] List of passed audits (button names, ARIA, color contrast, etc.)
- [ ] Zero failures

**Step-by-step**:
1. Open http://127.0.0.1:8080/tasks
2. Open DevTools → Lighthouse tab
3. Select "Accessibility" only (uncheck Performance, Best Practices, SEO)
4. Click "Generate report"
5. **Capture screenshot of results**
6. Save as `lighthouse_accessibility_score.png`

---

### Screenshot 7.2: axe DevTools Scan Results

**File name**: `axe_devtools_scan_results.png`  
**Scenario**: Automated accessibility testing with Deque axe  

**What to capture**:
- [ ] axe DevTools extension results
- [ ] 0 violations
- [ ] 0 warnings (or explanations for any warnings)
- [ ] Passed checks list

**Step-by-step**:
1. Install axe DevTools extension (free): https://www.deque.com/axe/devtools/
2. Open http://127.0.0.1:8080/tasks
3. Open DevTools → axe DevTools tab
4. Click "Scan ALL of my page"
5. **Capture screenshot**
6. Save as `axe_devtools_scan_results.png`

---

## Submission Preparation

### Organize Screenshots in Evidence Folder

Create directory structure:

```
wk11/gradescope/task2/evidence/
├── 1_nojs_success/
│   ├── P6_T2_nojs_success_full.png
│   ├── P6_T2_nojs_success_closeup.png
│   └── P6_T2_cookie_devtools.png
├── 2_nojs_error/
│   ├── P7_T2_nojs_error_full.png
│   ├── P7_T2_nojs_error_closeup.png
│   └── P7_T2_error_aria_inspect.png
├── 3_screen_reader/
│   ├── P8_T2_nvda_success_speech.png
│   ├── P8_T2_nvda_error_speech.png
│   └── P8_T2_nvda_elements_list.png
├── 4_keyboard/
│   ├── P9_T2_keyboard_tab_sequence.png
│   ├── P9_T2_keyboard_success_focus.png
│   └── P9_T2_keyboard_error_focus.png
├── 5_regression/
│   ├── P10_T2_htmx_inline_edit.png
│   ├── P10_T2_visual_200zoom_success.png
│   └── P10_T2_visual_200zoom_error.png
├── 6_comparison/
│   ├── before_nojs_no_success_message.png
│   └── after_nojs_success_message.png
└── 7_automated_testing/
    ├── lighthouse_accessibility_score.png
    └── axe_devtools_scan_results.png
```

### Compress Images if Needed

```powershell
# Check total size
Get-ChildItem -Recurse wk11\gradescope\task2\evidence\*.png | Measure-Object -Property Length -Sum

# If total > 50 MB, compress individual files using online tool:
# https://tinypng.com/ or https://squoosh.app/
```

### Create Index File

**File**: `wk11/gradescope/task2/evidence/INDEX.md`

```markdown
# Screenshot Evidence Index

| Category | File | Participant | WCAG Criterion | Status |
|----------|------|-------------|----------------|--------|
| No-JS Success | P6_T2_nojs_success_full.png | P6 | 4.1.3 | ✅ Captured |
| No-JS Success | P6_T2_nojs_success_closeup.png | P6 | 4.1.3 | ✅ Captured |
| No-JS Success | P6_T2_cookie_devtools.png | P6 | 4.1.3 | ✅ Captured |
| No-JS Error | P7_T2_nojs_error_full.png | P7 | 2.1.1, 3.3.1 | ✅ Captured |
| No-JS Error | P7_T2_nojs_error_closeup.png | P7 | 3.3.1 | ✅ Captured |
| No-JS Error | P7_T2_error_aria_inspect.png | P7 | 3.3.1 | ✅ Captured |
| Screen Reader | P8_T2_nvda_success_speech.png | P8 | 4.1.3 | ✅ Captured |
| Screen Reader | P8_T2_nvda_error_speech.png | P8 | 3.3.1 | ✅ Captured |
| Screen Reader | P8_T2_nvda_elements_list.png | P8 | 4.1.3 | ⚠️ Optional |
| Keyboard | P9_T2_keyboard_tab_sequence.png | P9 | 2.1.1 | ✅ Captured |
| Keyboard | P9_T2_keyboard_success_focus.png | P9 | 2.4.3 | ✅ Captured |
| Keyboard | P9_T2_keyboard_error_focus.png | P9 | 2.4.3 | ✅ Captured |
| Regression | P10_T2_htmx_inline_edit.png | P10 | N/A | ✅ Captured |
| Regression | P10_T2_visual_200zoom_success.png | P10 | 1.4.4 | ✅ Captured |
| Regression | P10_T2_visual_200zoom_error.png | P10 | 1.4.4 | ✅ Captured |
| Comparison | before_nojs_no_success_message.png | — | 4.1.3 | ✅ Captured |
| Comparison | after_nojs_success_message.png | — | 4.1.3 | ✅ Captured |
| Automated | lighthouse_accessibility_score.png | — | Multi | ✅ Captured |
| Automated | axe_devtools_scan_results.png | — | Multi | ✅ Captured |

**Total files**: 19 screenshots  
**Total size**: [TBD] MB  
**Status**: All required screenshots captured ✅
```

---

## Quality Checklist

Before submitting, verify each screenshot:

- [ ] **Resolution**: ≥1280×720 (readable text)
- [ ] **File size**: <2 MB (compressed if needed)
- [ ] **Naming**: Consistent, descriptive, no spaces
- [ ] **Context**: URL, date/time, or browser info visible
- [ ] **Focus**: Key feature clearly visible (not off-screen)
- [ ] **Annotations**: Red arrows/boxes highlighting important elements (optional)
- [ ] **Crop**: Removed unnecessary toolbars, taskbars (focus on content)
- [ ] **Privacy**: No PII visible (participant names, emails, etc.)

---

## Notes for User

**Time estimate**: 1–2 hours to capture all screenshots  
**Required tools**:
- Chrome or Firefox (with DevTools)
- NVDA screen reader (free download)
- Lighthouse (built into Chrome)
- axe DevTools (free extension)
- Screenshot tool (Windows Snipping Tool, macOS Command+Shift+4, or browser extensions)

**Tips**:
1. **Do Category 6 (Comparison) first** → Checkout old commit, capture "before" screenshot, checkout main, capture "after" screenshot
2. **Do Category 3 (Screen Reader) second** → Install NVDA, enable Speech Viewer, capture announcements
3. **Do Categories 1, 2, 4, 5 last** → Straightforward browser testing

**If you get stuck**: Some screenshots (like NVDA Elements List) are optional. Focus on critical evidence: success message, error announcement, focus outlines, before/after comparison.

---

**Document version**: 1.0  
**Last updated**: 2025-12-01  
**Status**: Template — awaiting user to capture screenshots  

---

**End of Screenshot Gallery Template**
