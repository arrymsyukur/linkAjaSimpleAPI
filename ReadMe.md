# Cara Install

### Prasyarat
1. Docker sudah terinstall pada Komputer anda

### Cara Running
1. Buka Terminal pada folder project ini
2. Lakukan Compile pada project maven
````
    .\mvnw clean install
````
3. Jalankan Docker Compose
````
    docker-compose up --build
````
4. Tunggu beberapa saat hingga aplikasi berjalan
5. Setelah Aplikasi berjalan, API bisa diakses melalui tautan
````
    http://localhost:8080/api/
````
6. Untuk mematikan aplikasi tekan Ctrl + C pada terminal, lalu masukan 
````
    docker-compose down
````