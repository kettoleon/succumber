import { SuccumberReportsUiPage } from './app.po';

describe('succumber-reports-ui App', () => {
  let page: SuccumberReportsUiPage;

  beforeEach(() => {
    page = new SuccumberReportsUiPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
