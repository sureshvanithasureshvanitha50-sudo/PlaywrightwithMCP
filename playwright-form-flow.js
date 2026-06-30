const { chromium, firefox, webkit } = require('playwright');

async function runScenario(browserType, browserName) {
  const browser = await browserType.launch({ headless: false });
  const context = await browser.newContext({ viewport: { width: 1440, height: 900 } });
  const page = await context.newPage();

  try {
    await page.goto('https://testautomationpractice.blogspot.com/', { waitUntil: 'domcontentloaded', timeout: 60000 });
    await page.waitForLoadState('networkidle', { timeout: 60000 });

    const title = await page.title();
    if (!title.includes('Automation Testing Practice')) {
      throw new Error(`Unexpected page title: ${title}`);
    }

    const heading = page.locator('h2.title, h3, h4').filter({ hasText: 'Data Entry Form' }).first();
    await heading.waitFor({ state: 'visible', timeout: 30000 });

    await page.locator('input[name="name"]').fill('Yamini');
    await page.locator('input[name="email"]').fill('email');
    await page.locator('input[name="phone"]').fill('1234567890');
    await page.locator('input[name="address"]').fill('address');

    await page.locator('input[value="female"]').check({ force: true });
    await page.locator('input[value="monday"]').check({ force: true });
    await page.locator('input[value="wednesday"]').check({ force: true });
    await page.locator('input[value="thursday"]').check({ force: true });

    await page.locator('select[name="country"], select').first().selectOption('France');

    console.log(`${browserName}: completed successfully`);
  } catch (error) {
    console.error(`${browserName}: ${error.message}`);
    throw error;
  } finally {
    await browser.close();
  }
}

(async () => {
  for (const [browserName, browserType] of [
    ['chromium', chromium],
    ['firefox', firefox],
    ['webkit', webkit],
  ]) {
    try {
      await runScenario(browserType, browserName);
    } catch (error) {
      console.error(`Failed in ${browserName}: ${error.message}`);
    }
  }
})();
