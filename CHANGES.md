# Patch 4

- Card design pages: new "Save sample card PDF" button at the bottom (works even with no codes, uses a sample card).
- Pinch with two fingers on the card preview to zoom (50% to 300%).
- More Arabic translations (archive, plan edit, delete/restore card, router connection errors, template variables help, and others).
- Added package-lock.json, so builds use the exact library versions (npm ci).

- Card design pages (Card, Style, Font, Template, Page) and the card preview now use the same header as the other screens: big bold title with the subtitle under it on the start side, then the zoom and download buttons, and the round logo at the end.
- Removed the zoom and download buttons from that header. Saving the PDF is still done from the big "Print / PDF" button at the bottom.

- No more flash of the print preview when the app opens. The app now shows nothing until it knows which screen to open: with saved router details it opens the Dashboard straight away and connects in the background (falls back to the Router screen if the connection fails); without them it opens the Router screen.

- Add Cards: the Profile selector moved from the main card into "More settings".
- Lighter app: Dashboard refreshes every 5 seconds instead of 3. The two background timers that ran every 0.3 s now run less often, skip work when nothing changed, and pause while the app is hidden. The blinking Live dot no longer animates. Looks are unchanged (blur kept).

# Patch 3 (Card Style)

- New tab **Card design > Style** to control every part of the default card, in both the preview and the PDF:
  - Card: background colors, border color and thickness, corner roundness, logo watermark (show / strength), move the middle part up or down.
  - Header: show or hide, height, color, name color and size, stars, line under it.
  - Code box: heading and code color and size, box color and border, dashed border, box width and height.
  - QR: size, left or right side, border color, text under it (show / your own text).
  - Plan bar and footer: show or hide, height, color, text color and size, or your own text.
- Colors marked Auto follow the main card color. "Reset card style" restores the original look. Settings are saved and included in the backup.

# Patch 2

- Removed "Create codes only (do not send)" and "Edit plans (profiles)".
- After saving the PDF from the print preview, the app returns to Add Cards (if the cards came from Create) or to History (if they came from Open for printing).

# Patch (print preview after adding)

- After you create cards (Create and add to router / Create codes only) or tap "Open for printing" in History, the sheet closes by itself so the cards fill the screen, and a big "Print / PDF" button with the card count appears at the bottom.
- The message toast sits above that button instead of covering it.

# Bright Star 1.6.0 (v6)

- **Easier to use**:
  - Add Cards now opens full screen with only the essentials: plan chips, count (with 10 / 28 / 50 / 100 shortcuts), profile and a live total (count x price). Everything else is under "More settings"; plan editing is under "Manage plans".
  - The "Create and add to router" button stays pinned at the bottom while you scroll.
  - Manage Cards: search is always on top; plan, batch and status filters are folded under "Filters". Delete-expired is red.
  - Home: today's sales tile is now the first card.

- **New look, modeled on the reference design**: indigo and cyan palette, soft frosted cards with large rounded corners, pill-style controls.
- **Home (Dashboard)** is now a full indigo screen with glass cards, a ring decoration behind the header, and 2x2 glass stat tiles.
- **Sales, History and Devices** open with an indigo summary card (white numbers, pill period switch on Sales).
- **Router connection** screen: onboarding-style card with geometric art and a pill "Test connection" button.
- **Floating bottom bar** with a white "+" button; the More menu is rounder and lighter. Dark mode follows the same palette.
- Traffic chart colors changed to cyan (download) and violet (upload).
- No change to the printed card designs, PDF output, or any app logic. Fonts are still the system fonts (the app works offline).

# Bright Star 1.5.0 (v5)

- **Sales is now its own page** (menu > Sales): period switch (today / 7 days / month), 14-day chart, revenue by plan, stock per plan with low-stock warning. The Dashboard only shows a small shortcut tile.
- **Visual refresh**: calmer palette, flatter cards, larger page titles, softer buttons and inputs, simpler router banner, cleaner dark mode.

## Earlier in this version

- **Sales**: cards used x plan price for today / last 7 days / this month. A card is counted the first time the app sees it as used (open the Dashboard regularly). Cards that were already used before the first run are not counted.
- **Low stock warning**: set a threshold on the Dashboard; plans with fewer unused cards show a warning.
- **Arabic PDF** in the default design (name, info, label and codes can contain Arabic).
- **Arabic / English interface**: button "العربية / English" in the menu. Switching restarts the screen. Strings that are not translated stay in English.
- **Automatic weekly backup** to Downloads/BrightStar (Android app only; can be turned off in History > Backup & restore). Backups now include the sales data.
- **Local network guard**: the app asks for confirmation before talking to a router address that is not on a private network.

Not done here: `package-lock.json` (needs internet). Run the first build on GitHub, download the `package-lock` artifact and commit it; later builds then use the exact versions.
