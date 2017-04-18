import { CuantoMemePage } from './app.po';

describe('cuanto-meme App', function() {
  let page: CuantoMemePage;

  beforeEach(() => {
    page = new CuantoMemePage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
