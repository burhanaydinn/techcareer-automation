## Techcareer Projesi

**Proje Hakkında:**

Page Object Model pattern kullanılmıştır.
Java ve Maven kullanarak oluşturulmuş bir otomasyon projesidir. Selenium, JUnit 5, Lombok ve Log4j gibi çeşitli kütüphaneleri kullanarak test senaryoları yazmanıza ve yürütmenize olanak tanır.

**Gereklilikler:**
* Java 11
* Maven


**Bağımlılıklar:**
* Selenium: 4.23.0
* JUnit 5: 5.10.1
* Lombok: 1.18.24
* Log4j: 2.22.0
* Gson: 2.10.1
* Automation(sukgu) : 0.1.5
* Allure Report : 2.25.0
* Aspect J : 1.9.21


**Test Case Yazımı:**
* https://www.cimri.com/ sayfası için;
* Test Senaryosu: Search alanından istenilen bir ürünü aratılarak, seçtiğimiz ürün ile ilgili “Mağazaya Git” akışı yazılmıştır.
* Test Senaryosu: Login için pozitif ve negatif senaryo akışları yazılmıştır.

**Otomasyon Test Süreci:**
* Yazılan fonksiyonel test case'inde (login/search) input adımları için gerekli olan data, csv ve excel dosyalarından alınmıştır.
* Otomasyon a alınan senaryoda tutar kontrolü ve kıyaslaması yapılmıştır.
* Otomasyon a alınan diğer senaryolarda login (pozitif/negatif) akışları yapılmıştır. 
* Yazılan otomasyon senaryosunun raporu Allure report kullanılarak hazırlanmıştır.

Not: Excel "cimri.xlsx" içerisindeki Senaryo sheet'inde tüm manuel senaryo adımları yer almaktadır.
