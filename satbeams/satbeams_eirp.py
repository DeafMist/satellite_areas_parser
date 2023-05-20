from selenium import webdriver
from selenium.webdriver.common.by import By
import pandas as pd
import time
option = webdriver.ChromeOptions()
option.add_argument("--disable-infobars") 
driver = webdriver.Chrome('\chromedriver.exe', chrome_options=option)

driver.get('https://www.satbeams.com/channels')

elem  =  driver.find_element(By.ID,  'modlgn_username' )
elem.send_keys('chancergame')
elem  =  driver.find_element(By.ID,  'modlgn_passwd' )
elem.send_keys('4M@sv4ymyBXbYZq')

share =  driver.find_element(By.CLASS_NAME,  'button' )
share.click()

start = driver.find_element(By.XPATH, '//td[@id="td_title"]/a[@class="nav_link"]')
start = start.text
current = ''
data = pd.DataFrame(columns=['name', 'eirp'])

while current != start:
    beams = driver.find_elements(By.XPATH, '//td[@class="group_td nobr"]/div[@class="group"]/div[7]/a')
    eirps = driver.find_elements(By.XPATH, '//td[@class="group_td nobr"]/div[@class="group"]/div[last()]')
    for i in range(len(beams)):
        a = beams[i].get_attribute('ext:qtip')
        a = a.partition('<b>')[2]
        a = a.partition('</b>')[0]
        b = eirps[i].text
        if 'NoC' in b:
            b = '0'
        elif 'EIRP' not in b:
            b = '-1'
        else:
            b = ''.join(x for x in b if x.isdigit() or x =='.')
        data.loc[ len(data.index )] = [a, b]
    print(data.sample(n=10))
    next = driver.find_element(By.XPATH, '//td[@id="td_next"]/a[@class="nav_link"]')
    next.click()
    time.sleep(6)
    current = driver.find_element(By.XPATH, '//td[@id="td_title"]/a[@class="nav_link"]')
    current = current.text

data.to_csv('satbeams_eirp.csv', index=False)