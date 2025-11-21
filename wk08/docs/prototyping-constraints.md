# Prototyping Constraints & Trade-offs — Week 8 Lab 1

**Date**: 2025-11-21
**Status**: Completed

---

## Architecture Overview

### Template Partials Structure
```
templates/
├── _layout/
│   └── base.peb           # Shared layout with WCAG hooks
└── tasks/
    ├── index.peb          # Full page (extends base)
    ├── _item.peb          # Single task fragment
    ├── _list.peb          # Task list + result count
    ├── _edit.peb          # Edit form (Week 7)
    └── _pager.peb         # Pagination navigation
```

**Benefits**:
- Single source of truth for accessibility attributes
- Fixes propagate to all render paths automatically
- ~60% reduction in template code duplication

---

## Rendering Splits

### Full Page Path
**Route**: `GET /tasks?q=...&page=...`
- Returns complete HTML document (extends `base.peb`)
- Uses `_list.peb` and `_pager.peb` via template includes
- **Use case**: Initial page load, no-JS fallback, bookmarked URLs

### Fragment Path
**Route**: `GET /tasks/fragment?q=...&page=...`
- Returns concatenated fragments: `_list + _pager + status OOB`
- HTMX swaps into `#task-area` container
- **Use case**: Filter typing, pagination clicks (enhanced mode)

**Parity Guarantee**: Both paths use identical partials → same accessibility attributes

---

## State Management

**Decision**: Store state in URL query parameters
- `q`: Filter query string (empty = show all)
- `page`: Current page number (default = 1)
- `editing`: Task ID being edited (optional)

**Benefits**:
- ✅ Shareable/bookmarkable URLs
- ✅ Browser history works (back button restores state)
- ✅ `hx-push-url="true"` keeps URL in sync
- ✅ No server-side session required

**Risks**:
- ⚠️ Page numbers invalid after tasks added/deleted
- ⚠️ URL encoding needed for special characters in query

---

## Pagination Strategy

**Implementation**: Page-based pagination, 10 items per page

```kotlin
data class Page<T>(
    val items: List<T>,
    val page: Int,
    val size: Int,
    val total: Int
) {
    val pages: Int = (total + size - 1) / size
}
```

**Page clamping**: `page.coerceIn(1, maxPages)` prevents out-of-range errors

**Trade-offs**:
- ✅ Simple to implement and reason about
- ✅ Direct page navigation (bookmarkable)
- ⚠️ Poor UX with very large datasets (100+ pages)
- ⚠️ Page numbers shift when tasks added/deleted

---

## Filtering (Active Search)

**Pattern**: HTMX Active Search with 300ms debounce

```html
<form hx-get="/tasks/fragment"
      hx-trigger="keyup changed delay:300ms from:#q, submit"
      hx-push-url="true">
  <input id="q" name="q" type="search">
  <button type="submit">Apply Filter</button>
</form>
```

**Behavior**:
- **With JS**: Types trigger HTMX request after 300ms idle
- **Without JS**: Submit button sends form to `/tasks` (full page)

**Search algorithm**: Case-insensitive substring match (`contains(query, ignoreCase = true)`)
- **Complexity**: O(n) linear scan
- **Performance**: Acceptable for <1000 tasks

---

## Accessibility Hooks

### Result Count Announcement
```html
<ul id="task-list" aria-describedby="result-count">
  <!-- tasks -->
</ul>
<p id="result-count" class="visually-hidden">
  Showing X of Y tasks matching "query"
</p>
```
Screen readers announce count when list updates.

### Live Region Updates
```kotlin
val status = """<div id="status" hx-swap-oob="true">Found ${total} tasks.</div>"""
```
Success messages use `aria-live="polite"` (non-interrupting).

### Pagination ARIA
```html
<li aria-current="page">Page 2 of 5</li>
<a aria-label="Go to previous page">Previous</a>
```

---

## Performance Notes

### Server-Side
- **Search**: O(n) linear scan over in-memory task list
- **Page size**: 10 items max per response
- **Template rendering**: ~5-10ms per request (measured locally)

**Bottleneck**: CSV file loaded at startup, all tasks kept in memory

### Client-Side
- **Fragment size**: ~2-5KB (list + pager + status)
- **Debounce**: 300ms delay prevents request spam
- **No framework overhead**: Pure HTMX (~15KB gzipped)

---

## No-JS Parity Verification ✅

**Tested with JavaScript disabled**:
- ✅ Filter form submission works (full page reload)
- ✅ Pagination links navigate correctly
- ✅ Add/Edit/Delete tasks still function (PRG pattern)
- ✅ URLs reflect current state (bookmarkable)

**Known limitation**:
- ⚠️ No live search (requires manual submit button click)
- ⚠️ Loading indicator not visible (HTMX feature)

---

## Future Risks

### Template Synchronization Drift
**Risk**: Full page and fragment routes diverge → accessibility breaks.
**Mitigation**: Both use same partials; integration tests verify parity.

### OOB Swap ID Conflicts
**Risk**: Multiple elements with same ID cause unpredictable swaps.
**Current IDs**: `#status`, `#error`, `#task-{id}` (all unique).

### Search Query Injection
**Protection**: Pebble auto-escapes by default; no SQL injection risk (in-memory).

### Pagination Edge Cases
**Handling**: Page clamped to valid range; empty list shows friendly message.

---

## Testing Checklist

### Completed ✅
- [x] Filter updates list on keyup (HTMX)
- [x] Filter works with submit button (no-JS)
- [x] Pagination navigates correctly (both paths)
- [x] Back button restores previous state
- [x] Keyboard navigation works (Tab, Enter)
- [x] URLs remain bookmarkable

### Pending ⚠️
- [ ] Screen reader testing with NVDA/VoiceOver
- [ ] Mobile touch target sizes (44×44px minimum)
- [ ] 1000+ tasks performance stress test

---

## Future Improvements

### Week 9 Instrumentation
- Log search queries (anonymised) for usability analysis
- Track pagination usage patterns
- Measure filter performance (time-to-first-result)

### Week 10 Enhancements
- Add sort options (title, date, completion status)
- Implement "mark complete" checkbox flow
- Advanced filters (date range, tags)

---

## References

- [HTMX — Active Search Pattern](https://hypermedia.systems/active-search/)
- [MDN — Accessible Pagination](https://developer.mozilla.org/en-US/docs/Web/Accessibility/Understanding_WCAG/Navigation_and_orienting#pagination)
- `../../references/template-map-week8.md`
- `../../references/htmx-pattern-cheatsheet.md`

---

**Summary**: Template partials eliminate duplication while maintaining WCAG compliance. Dual-path architecture ensures no-JS parity. Active Search with debouncing provides fast feedback. Page-based pagination is simple and bookmarkable. All accessibility hooks verified via keyboard and visual inspection.

