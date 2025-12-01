# Week 11 Lab 2: Gradescope Task 2 Submission Checklist

**Purpose**: Ensure all required evidence is collected before submitting to Gradescope  
**Deadline**: [Insert your course deadline]  
**Submission URL**: [Insert Gradescope Task 2 URL]  

---

## Pre-Submission Checklist

### Phase 1: Documentation Files

**Core documents** (must be PDF):

- [ ] **executive-summary.pdf** (3–5 pages)
  - [ ] Problem statement with quantitative evidence (completion rates, error rates)
  - [ ] Prioritization methodology ((Impact + Inclusion) – Effort scoring)
  - [ ] Implementation details (code changes, WCAG mapping)
  - [ ] Verification evidence (regression tests, before/after metrics)
  - [ ] Reflection (what went well, what to improve, lessons learned)
  - [ ] Evidence artifact index (file list with commit hashes)
  - [ ] Academic integrity statement signed
  - [ ] File size < 10 MB

- [ ] **code-diff.pdf** (compiled from code-diff.md)
  - [ ] All 6 file changes documented (TaskRoutes.kt, index.peb, _edit.peb, _list.peb)
  - [ ] Before/after code snippets with line numbers
  - [ ] Rationale for each change
  - [ ] WCAG criterion mapped to each change
  - [ ] Testing notes for each change
  - [ ] Trade-offs and design decisions explained
  - [ ] File size < 5 MB

- [ ] **screenshot-gallery.pdf** (compiled from screenshot-gallery.md + images)
  - [ ] All 19 screenshots embedded (or separate ZIP if too large)
  - [ ] Each screenshot annotated with red arrows/boxes
  - [ ] INDEX.md showing all files with status
  - [ ] File size < 50 MB (compress images if needed)

**Supporting documents**:

- [ ] **prioritization.md** (from Week 10 Lab 1)
  - [ ] Scoring matrix for all 5 issues
  - [ ] Rationale for Priority 1 focus (Issues #1 and #2)
  - [ ] Evidence links (pilot quotes, metrics rows)

- [ ] **regression-checklist.md** (from Week 10 Lab 2)
  - [ ] All 6 test areas completed
  - [ ] Expected vs Actual results filled in
  - [ ] Status (PASS/FAIL) marked for each test

- [ ] **before-after-metrics.md** (from Week 10 Lab 2)
  - [ ] Before metrics (Week 9 baseline) filled in
  - [ ] After metrics filled in (or clearly marked TBD with explanation)
  - [ ] Δ (delta) column calculated
  - [ ] Interpretation notes for each metric

---

### Phase 2: Evidence Files

**Raw data**:

- [ ] **data/metrics-mock.csv** (23 events, 5 participants)
  - [ ] All 23 rows present
  - [ ] Headers match expected format (ts_iso, session_id, task_code, etc.)
  - [ ] File size < 100 KB

- [ ] **analysis/analysis.csv** (12 summary rows)
  - [ ] All 12 rows present (4 tasks × 3 js_modes)
  - [ ] Columns: task_code, js_mode, n_success, n_errors, n_fail, n_total, median_ms, mad_ms, min_ms, max_ms, completion_rate, error_rate
  - [ ] File size < 10 KB

- [ ] **analysis/findings.md** (430 lines)
  - [ ] Quantitative findings with tables
  - [ ] Qualitative findings with pilot quotes
  - [ ] Issue prioritization (5 issues scored)
  - [ ] Redesign brief (scope for Week 10 Lab 2)
  - [ ] Evidence chain documentation

**Code artifacts**:

- [ ] **Git commit hashes verified**:
  - [ ] ae1308a: "feat: implement Priority 1 fixes (no-JS success + error announcements)"
  - [ ] 93beb04: "docs: create regression checklist and before-after metrics template"
  - [ ] 9c63647: "docs: add Week 10 completion summary"
  - [ ] Use `git log --oneline` to confirm

- [ ] **Git diff extracted** (optional, for reviewers):
  - [ ] Run: `git diff ae1308a~1 ae1308a > priority1-fixes.diff`
  - [ ] File size < 50 KB

**Screenshot artifacts** (19 total):

- [ ] **Category 1: No-JS Success** (3 screenshots)
  - [ ] P6_T2_nojs_success_full.png
  - [ ] P6_T2_nojs_success_closeup.png
  - [ ] P6_T2_cookie_devtools.png

- [ ] **Category 2: No-JS Error** (3 screenshots)
  - [ ] P7_T2_nojs_error_full.png
  - [ ] P7_T2_nojs_error_closeup.png
  - [ ] P7_T2_error_aria_inspect.png

- [ ] **Category 3: Screen Reader** (3 screenshots)
  - [ ] P8_T2_nvda_success_speech.png
  - [ ] P8_T2_nvda_error_speech.png
  - [ ] P8_T2_nvda_elements_list.png (optional)

- [ ] **Category 4: Keyboard** (3 screenshots)
  - [ ] P9_T2_keyboard_tab_sequence.png
  - [ ] P9_T2_keyboard_success_focus.png
  - [ ] P9_T2_keyboard_error_focus.png

- [ ] **Category 5: Regression** (3 screenshots)
  - [ ] P10_T2_htmx_inline_edit.png
  - [ ] P10_T2_visual_200zoom_success.png
  - [ ] P10_T2_visual_200zoom_error.png

- [ ] **Category 6: Comparison** (2 screenshots)
  - [ ] before_nojs_no_success_message.png
  - [ ] after_nojs_success_message.png

- [ ] **Category 7: Automated Testing** (2 screenshots)
  - [ ] lighthouse_accessibility_score.png
  - [ ] axe_devtools_scan_results.png

**Screenshot quality checks**:

- [ ] All images ≥1280×720 resolution
- [ ] All images <2 MB each (compressed if needed)
- [ ] All filenames descriptive, no spaces
- [ ] All images have annotations (red arrows/boxes) where needed
- [ ] Total screenshot folder <50 MB

---

### Phase 3: WCAG Compliance Evidence

**WCAG 2.2 Level AA checklist**:

- [ ] **2.1.1 Keyboard (Level A)**: No-JS parity restored
  - [ ] Evidence: P6_T2_nojs_success_full.png, P9_T2_keyboard_tab_sequence.png
  - [ ] Test result: PASS

- [ ] **3.3.1 Error Identification (Level A)**: Validation errors announced
  - [ ] Evidence: P8_T2_nvda_error_speech.png, P7_T2_error_aria_inspect.png
  - [ ] Test result: PASS

- [ ] **4.1.3 Status Messages (Level AA)**: Success messages programmatically determined
  - [ ] Evidence: P8_T2_nvda_success_speech.png, P6_T2_nojs_success_closeup.png
  - [ ] Test result: PASS

- [ ] **2.4.3 Focus Order (Level A)**: Focus moves logically
  - [ ] Evidence: P9_T2_keyboard_success_focus.png, P9_T2_keyboard_error_focus.png
  - [ ] Test result: PASS

- [ ] **1.4.4 Resize Text (Level AA)**: No loss of content at 200% zoom
  - [ ] Evidence: P10_T2_visual_200zoom_success.png, P10_T2_visual_200zoom_error.png
  - [ ] Test result: PASS

**Automated testing**:

- [ ] **Lighthouse Accessibility**: Score ≥90 (ideally 100)
  - [ ] Evidence: lighthouse_accessibility_score.png
  - [ ] 0 failures

- [ ] **axe DevTools**: 0 violations
  - [ ] Evidence: axe_devtools_scan_results.png
  - [ ] 0 warnings (or explanations for any warnings)

---

### Phase 4: Reflection Quality

**Reflection document checks** (in executive-summary.md, Section 5):

- [ ] **What went well** (3+ items):
  - [ ] Data-driven prioritization made decisions clear
  - [ ] Small code changes, big impact (40 lines → 33pp improvement)
  - [ ] Evidence chains clarified communication

- [ ] **What to improve** (3+ items):
  - [ ] Need real participants for after-metrics
  - [ ] Should have tested more screen readers (JAWS, VoiceOver)
  - [ ] Documentation could use more inline comments

- [ ] **Theory-practice integration**:
  - [ ] WCAG 2.2 AA criteria cited (with section numbers)
  - [ ] Progressive enhancement principle explained
  - [ ] (Impact + Inclusion) – Effort framework referenced
  - [ ] Academic sources cited (e.g., Nielsen 1993 for n=5)

- [ ] **Next steps** (realistic roadmap):
  - [ ] Verification pilots (Semester 2)
  - [ ] JAWS/VoiceOver testing
  - [ ] Issue #3 implementation (required indicator)

- [ ] **Honest about limitations**:
  - [ ] Small sample size (n=5, formative evaluation)
  - [ ] Mock data used (real pilots pending)
  - [ ] After-metrics marked TBD with explanation

---

### Phase 5: File Organization

**Directory structure** (final check):

```
wk11/gradescope/task2/
├── executive-summary.pdf          ← Compiled from .md
├── code-diff.pdf                  ← Compiled from .md
├── screenshot-gallery.pdf         ← Compiled from .md + images
├── prioritization.md              ← From Week 10 Lab 1
├── regression-checklist.md        ← From Week 10 Lab 2
├── before-after-metrics.md        ← From Week 10 Lab 2
├── evidence/
│   ├── 1_nojs_success/
│   │   ├── P6_T2_nojs_success_full.png
│   │   ├── P6_T2_nojs_success_closeup.png
│   │   └── P6_T2_cookie_devtools.png
│   ├── 2_nojs_error/
│   │   ├── P7_T2_nojs_error_full.png
│   │   ├── P7_T2_nojs_error_closeup.png
│   │   └── P7_T2_error_aria_inspect.png
│   ├── 3_screen_reader/
│   │   ├── P8_T2_nvda_success_speech.png
│   │   ├── P8_T2_nvda_error_speech.png
│   │   └── P8_T2_nvda_elements_list.png
│   ├── 4_keyboard/
│   │   ├── P9_T2_keyboard_tab_sequence.png
│   │   ├── P9_T2_keyboard_success_focus.png
│   │   └── P9_T2_keyboard_error_focus.png
│   ├── 5_regression/
│   │   ├── P10_T2_htmx_inline_edit.png
│   │   ├── P10_T2_visual_200zoom_success.png
│   │   └── P10_T2_visual_200zoom_error.png
│   ├── 6_comparison/
│   │   ├── before_nojs_no_success_message.png
│   │   └── after_nojs_success_message.png
│   ├── 7_automated_testing/
│   │   ├── lighthouse_accessibility_score.png
│   │   └── axe_devtools_scan_results.png
│   └── INDEX.md
├── data/
│   ├── metrics-mock.csv           ← From Week 10 Lab 1
│   └── (optional: real pilot data if available)
├── analysis/
│   ├── analysis.csv               ← From Week 10 Lab 1
│   ├── findings.md                ← From Week 10 Lab 1
│   └── analyze-simple.py          ← From Week 10 Lab 1
└── README.md                      ← Navigation guide (optional)
```

**File size checks**:

- [ ] `executive-summary.pdf`: < 10 MB
- [ ] `code-diff.pdf`: < 5 MB
- [ ] `screenshot-gallery.pdf`: < 50 MB (or separate ZIP)
- [ ] Total `evidence/` folder: < 50 MB
- [ ] **Grand total**: < 100 MB (Gradescope limit)

**File naming checks**:

- [ ] All filenames lowercase (optional, but consistent)
- [ ] No spaces in filenames (use underscores or hyphens)
- [ ] Descriptive names (e.g., `P6_T2_nojs_success_full.png`, not `Screenshot1.png`)

---

### Phase 6: Compilation to PDF

**Convert Markdown to PDF** (required for Gradescope):

**Option A: Pandoc** (recommended):
```powershell
# Install Pandoc: https://pandoc.org/installing.html
# Then run:
pandoc executive-summary.md -o executive-summary.pdf --pdf-engine=xelatex
pandoc code-diff.md -o code-diff.pdf --pdf-engine=xelatex
```

**Option B: VS Code Markdown PDF Extension**:
1. Install "Markdown PDF" extension (yzane.markdown-pdf)
2. Open `executive-summary.md`
3. Right-click → "Markdown PDF: Export (pdf)"
4. Repeat for `code-diff.md`

**Option C: Print to PDF**:
1. Open Markdown preview in VS Code or browser
2. Ctrl+P (Print)
3. Select "Save as PDF"
4. Save to `wk11/gradescope/task2/`

**PDF quality checks**:

- [ ] Code blocks formatted (monospace font, syntax highlighting)
- [ ] Tables formatted (borders visible, columns aligned)
- [ ] Images embedded (screenshots visible, not broken links)
- [ ] Headers/footers correct (page numbers, document title)
- [ ] Fonts render correctly (no missing glyphs)
- [ ] Links clickable (if needed, e.g., Git commit URLs)

---

### Phase 7: Final Review

**Proofreading**:

- [ ] Spell-check all documents (no typos in executive-summary.pdf)
- [ ] Grammar check (clear, professional writing)
- [ ] Consistent terminology (e.g., "no-JS" not "noJS" or "no JavaScript")
- [ ] Acronyms defined on first use (WCAG, ARIA, NVDA, etc.)
- [ ] Citations complete (WCAG criterion numbers, commit hashes, pilot IDs)

**Evidence chain validation**:

For each claim in executive-summary.pdf, verify:
- [ ] Quantitative claim → CSV row cited (e.g., "metrics-mock.csv Row 12")
- [ ] Qualitative claim → Pilot quote cited (e.g., "Participant P3: '...'")
- [ ] Code change → Commit hash cited (e.g., "ae1308a, TaskRoutes.kt Line 380")
- [ ] WCAG compliance → Screenshot cited (e.g., "P8_T2_nvda_error_speech.png")

**Peer review** (optional but recommended):

- [ ] Ask a classmate to review executive-summary.pdf
- [ ] Check for: clarity, completeness, evidence traceability
- [ ] Address feedback before submitting

---

### Phase 8: Gradescope Submission

**Submission steps**:

1. [ ] Log in to Gradescope: [Insert course URL]
2. [ ] Navigate to "COMP2850 → Task 2: Redesign & Verification"
3. [ ] Upload files:
   - [ ] `executive-summary.pdf` (primary document)
   - [ ] `code-diff.pdf` (supplementary)
   - [ ] `screenshot-gallery.pdf` OR `evidence.zip` (if screenshots separate)
   - [ ] (Optional: `regression-checklist.md`, `before-after-metrics.md`, `prioritization.md`)
4. [ ] Verify upload (all files visible in Gradescope preview)
5. [ ] Match pages to rubric items (if Gradescope requires):
   - [ ] Problem Statement → Page 1-2 of executive-summary.pdf
   - [ ] Prioritization → Page 3-4
   - [ ] Implementation → Page 5-7
   - [ ] Verification → Page 8-10
   - [ ] Reflection → Page 11-12
6. [ ] Submit
7. [ ] **Save confirmation email/screenshot** (proof of submission)

**Post-submission**:

- [ ] Verify submission status (Gradescope shows "Submitted")
- [ ] Check for any auto-grader feedback (if enabled)
- [ ] Backup all files to external drive or cloud storage
- [ ] Celebrate! 🎉

---

## Troubleshooting

### Issue: PDF too large (>100 MB)

**Solution**:
1. Compress screenshots using https://tinypng.com/ or https://squoosh.app/
2. Reduce screenshot resolution to 1280×720 (if currently higher)
3. Submit screenshots as separate ZIP file instead of embedded PDF
4. Remove redundant screenshots (keep only critical evidence)

**Command to check size**:
```powershell
Get-ChildItem -Recurse wk11\gradescope\task2\*.png | Measure-Object -Property Length -Sum
```

---

### Issue: Pandoc compilation fails

**Error**: "xelatex not found"

**Solution**:
1. Install MiKTeX (Windows): https://miktex.org/download
2. Or install TeX Live (cross-platform): https://www.tug.org/texlive/
3. Restart terminal after installation
4. Re-run pandoc command

**Alternative**: Use VS Code Markdown PDF extension (no LaTeX required)

---

### Issue: Screenshots have wrong resolution

**Error**: Screenshots are 800×600 (too small, text unreadable)

**Solution**:
1. Set browser zoom to 100% before capturing
2. Use full-screen mode (F11) to maximize viewport
3. Use Windows Snipping Tool (Win+Shift+S) or macOS Command+Shift+4
4. Check resolution after capture: Right-click image → Properties → Details

**Target resolution**: ≥1280×720 (HD), ideally 1920×1080 (Full HD)

---

### Issue: Git commits missing

**Error**: Cannot find ae1308a commit hash

**Solution**:
1. Run `git log --oneline` to list all commits
2. If commit not found, check you're on correct branch: `git branch`
3. If on wrong branch, switch: `git checkout main`
4. If commit genuinely missing, re-do Week 10 Lab 2 fixes and commit again

---

### Issue: NVDA not announcing messages

**Error**: NVDA silent when testing success/error messages

**Solution**:
1. Check NVDA Speech Viewer is open: NVDA menu (Insert+N) → Tools → Speech Viewer
2. Verify ARIA attributes in DevTools: `role="status"` or `role="alert"` present
3. Try refreshing page after NVDA starts (NVDA doesn't detect live regions loaded before it started)
4. Alternative: Use Narrator (Windows built-in) or record video with system audio

---

## Time Estimates

| Phase | Task | Time |
|-------|------|------|
| 1 | Documentation files (review and edit) | 30 min |
| 2 | Evidence files (verify CSV, git commits) | 15 min |
| 3 | WCAG compliance checks | 20 min |
| 4 | Reflection quality review | 20 min |
| 5 | File organization | 10 min |
| 6 | Compilation to PDF | 15 min |
| 7 | Final review (proofreading, peer review) | 30 min |
| 8 | Gradescope submission | 10 min |
| **Total** | | **~2.5 hours** |

**Note**: Does NOT include time to capture screenshots (add 1–2 hours if screenshots not yet taken)

---

## Submission Confirmation

After completing all checklist items, sign below:

**I confirm that**:
- All required files are included and correctly formatted
- All evidence is traceable (pilot IDs, commit hashes, WCAG criteria cited)
- All screenshots are legible and correctly annotated
- Reflection demonstrates learning (theory connected to practice)
- Total submission size < 100 MB
- Academic integrity statement signed in executive-summary.pdf

**Signature**: ___________________  
**Date**: ___________________  
**Submission timestamp**: ___________________

---

**Checklist version**: 1.0  
**Last updated**: 2025-12-01  
**Total items**: 150+ checkboxes  

---

**🎯 Good luck with your submission!**
