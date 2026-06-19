# 🗡️ Zindan Gezgini (Dungeon Crawler)

Java ve Nesne Yönelimli Programlama (OOP) prensipleri kullanılarak geliştirilmiş, sürdürülebilir yazılım mimarisine odaklanan zindan keşif oyunu. 

## 🚀 Proje Amacı ve Teknik Özet
Bu projenin temel amacı sadece bir oyun mekaniği kurgulamak değil; endüstri standardı **Yazılım Tasarım Kalıplarının (Design Patterns)** oyun motoru mantığına nasıl doğru ve verimli bir şekilde entegre edilebileceğini kanıtlamaktır.

## 🛠 Kullanılan Teknolojiler
* **Dil:** Java
* **Proje Yönetimi:** Maven
* **Mimari Standartlar:** OOP, SOLID Prensipleri, GoF Design Patterns

## 🔑 Öne Çıkan Mimari Özellikler (Key Features & Patterns)
Proje, kodun esnekliğini ve yeniden kullanılabilirliğini (reusability) artırmak için 3 farklı kategoride tasarım kalıbı kullanılarak inşa edilmiştir:

* **🛠️ Creational (Oluşturucu) - Factory Method:** 
  Oyun içindeki farklı düşman tiplerinin ve eşyaların yaratım süreçleri soyutlanmış; nesne oluşturma mantığı istemciden (client) gizlenerek sistemin genişletilebilirliği artırılmıştır.
  
* **🛡️ Structural (Yapısal) - Decorator:** 
  Karakter yetenekleri ve silah özellikleri (ör. ekstra ateş hasarı, zırh eklemeleri) alt sınıflara (subclass) boğulmadan, çalışma zamanında (runtime) dinamik ve esnek bir şekilde objelere giydirilmiştir.
  
* **⚔️ Behavioral (Davranışsal) - Strategy:** 
  Karakterlerin veya düşmanların savaş taktikleri ve hareket algoritmaları çalışma zamanında değiştirilebilir (interchangeable) hale getirilmiş, karmaşık if-else blokları yerine temiz bir strateji mimarisi kurulmuştur.