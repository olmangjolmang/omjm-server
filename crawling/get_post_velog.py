import os
import requests
from bs4 import BeautifulSoup
from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from webdriver_manager.chrome import ChromeDriverManager
import time
import sys
import io
import csv

sys.stdout = io.TextIOWrapper(sys.stdout.detach(),encoding="utf-8")
sys.stdin = io.TextIOWrapper(sys.stdin.detach(),encoding="utf-8")

# csv 읽어오기
f = open("velog_url.csv", "r",encoding='utf-8-sig')
reader = csv.reader(f)
data = list(reader)[0][:5]

# 크롬 가져오기
service = Service(executable_path='./chromedriver.exe')
driver = webdriver.Chrome(service=service)

folder_name = "velog_post"
for idx,base_url in enumerate(data):
    # print(base_url)
    driver.get(base_url)
    # driver.implicitly_wait(3)
    time.sleep(10)
    html = driver.page_source
    soup = BeautifulSoup(html, 'html.parser')

    tmp = soup.find_all("div",class_ ="sc-dFtzxp bfzjcP")
    
    filename = os.path.join(folder_name, f"{idx}.txt")
    with open(filename, 'w', encoding='utf-8') as file:
        file.write(f"IDX : {base_url}\n\n{tmp}")
    exit()
    