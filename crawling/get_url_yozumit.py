import os, time, io, sys, csv
from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.action_chains import ActionChains
from webdriver_manager.chrome import ChromeDriverManager
from bs4 import BeautifulSoup
import pandas as pd

sys.stdout = io.TextIOWrapper(sys.stdout.detach(), encoding="utf-8")
sys.stdin = io.TextIOWrapper(sys.stdin.detach(), encoding="utf-8")

service = Service(executable_path=ChromeDriverManager().install())
options = webdriver.ChromeOptions()
options.add_argument('--headless')
options.add_argument('--disable-gpu')
options.add_argument('--log-level=3')
driver = webdriver.Chrome(service=service, options=options)


# 여기서 range의 값을 조절함으로써 다음 페이지로 넘길 수 있음
for i in range(1, 21):  # 200개
    base_url = f'https://yozm.wishket.com/magazine/list/develop?page={i}&sort=&q='
    driver.get(base_url)
    time.sleep(2)

    # 페이지 로딩을 기다립니다.
    WebDriverWait(driver, 10).until(
        EC.presence_of_element_located((By.CSS_SELECTOR, "div.list-item"))
    )

    # 스크롤을 내려 추가 콘텐츠를 로드합니다.
    for _ in range(5):  # 필요에 따라 스크롤 횟수를 조정하세요.
        actions = ActionChains(driver)
        actions.send_keys(Keys.PAGE_DOWN).perform()
        time.sleep(1)

    html = driver.page_source
    soup = BeautifulSoup(html, 'html.parser')

    # list 형태로 tmp에 담김
    post_link_tag = soup.select("div.list-item > div:nth-child(1) > div:nth-child(1) > a:nth-child(1)")
    post_links = ["https://yozm.wishket.com" + link.get('href') for link in post_link_tag]

    post_title_tag = soup.select("div.list-item > div:nth-child(1) > div:nth-child(1) > a")
    post_titles = [title.get_text() for title in post_title_tag]

    post_author_tag = soup.select("p.author-name > a")
    post_authors = [author.get_text() for author in post_author_tag]

    post_date_tag = soup.select("div.content-meta > div:nth-child(4)")
    post_dates = [date.get_text() for date in post_date_tag]

    post_img_tag = soup.select("div.list-item > div:nth-child(1) > div:nth-child(2) > a > img")
    post_imgs = ["https://yozm.wishket.com" + img.get("src") for img in post_img_tag]
    print(post_imgs)
    exit()

print(f"Links: {len(post_links)}")
print(f"Titles: {len(post_titles)}")
print(f"Authors: {len(post_authors)}")
print(f"Dates: {len(post_dates)}")
print(f"Images: {len(post_imgs)}")


# 데이터 길이가 같도록 조정
min_length = min(len(post_links), len(post_titles), len(post_authors), len(post_dates))
df = pd.DataFrame({
    "hyper-link": post_links[:min_length],
    "title": post_titles[:min_length],
    "author": post_authors[:min_length],
    "createdDate": post_dates[:min_length],
    "image-url" : post_imgs[:min_length]
})

df.to_csv("./yozum_it.csv", encoding='utf-8-sig', index=False)

driver.quit()
