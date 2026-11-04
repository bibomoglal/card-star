// Copies the three browser libraries from node_modules into www/lib so the app works without internet.
const fs = require('fs'), path = require('path');
fs.mkdirSync('www/lib', { recursive: true });
function pick(label, dest, candidates) {
  const f = candidates.find(p => fs.existsSync(p));
  if (!f) { console.error('Missing ' + label + ' in node_modules (run npm install first). Looked in: ' + candidates.join(', ')); process.exit(1); }
  fs.copyFileSync(f, path.join('www/lib', dest));
  console.log(label + ' -> www/lib/' + dest + ' (' + fs.statSync(f).size + ' bytes)');
}
pick('jsPDF', 'jspdf.umd.min.js', ['node_modules/jspdf/dist/jspdf.umd.min.js']);
pick('html2canvas', 'html2canvas.min.js', ['node_modules/html2canvas/dist/html2canvas.min.js']);
pick('qrcode-generator', 'qrcode.min.js', ['node_modules/qrcode-generator/qrcode.js', 'node_modules/qrcode-generator/dist/qrcode.js']);
