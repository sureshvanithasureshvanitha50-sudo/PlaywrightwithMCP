const { chromium } = require('playwright');
(async () => {
  const browser = await chromium.launch({ headless: true });
  const page = await browser.newPage();
  await page.goto('https://testautomationpractice.blogspot.com/', { waitUntil: 'domcontentloaded', timeout: 60000 });
  await page.waitForTimeout(5000);
  console.log('TITLE:', await page.title());
  console.log('BODY_SNIPPET:');
  console.log((await page.locator('body').innerText()).slice(0, 6000));
  const headings = await page.locator('h1, h2, h3, h4').evaluateAll(nodes => nodes.map(n => ({ text: n.textContent.trim(), tag: n.tagName })));
  console.log('HEADINGS:', JSON.stringify(headings, null, 2));
  const inputs = await page.locator('input').evaluateAll(nodes => nodes.map(n => ({ type: n.type, name: n.name, id: n.id, placeholder: n.placeholder, value: n.value, className: n.className, ariaLabel: n.getAttribute('aria-label') })));
  console.log('INPUTS:', JSON.stringify(inputs, null, 2));
  const selects = await page.locator('select').evaluateAll(nodes => nodes.map(n => ({ name: n.name, id: n.id, options: Array.from(n.options).map(o => ({ value: o.value, text: o.text })) })));
  console.log('SELECTS:', JSON.stringify(selects, null, 2));
  await browser.close();
})();
