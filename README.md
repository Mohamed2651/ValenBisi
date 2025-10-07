# ValenBici 🚲

[![Versión](https://img.shields.io/badge/version-1.0-blue)](https://github.com/Mohamed2651/ValenBici/releases)  
[![Descargar APK](https://img.shields.io/badge/Descargar-APK-green?logo=android)](https://github.com/Mohamed2651/ValenBici/raw/main/apk/ValenBici.apk)

ValenBici es una aplicación Android que permite consultar en tiempo real la disponibilidad de bicicletas y espacios libres en las estaciones de ValenBisi, el sistema de bicicletas públicas de Valencia.

---

## Características

- Lista de estaciones con dirección, bicicletas disponibles y espacios libres.
- Colores visuales que indican la disponibilidad de bicicletas:
  - 🔴 Rojo: Sin bicicletas disponibles
  - 🟠 Naranja: Entre 1 y 4 bicicletas
  - 🟡 Amarillo: Entre 5 y 9 bicicletas
  - 🟢 Verde: 10 o más bicicletas
- Buscador en tiempo real para filtrar estaciones por dirección.
- Botón de refresco para actualizar los datos manualmente.

---

## Tecnologías

- **Lenguaje:** Kotlin  
- **Interfaz:** Material Design, Toolbar y CardView  
- **API:** ValenBisi Open Data API  
- **Arquitectura:** RecyclerView con Adapter personalizado  

---

## Autor y Fecha

- **Autor:** Mohammed Shahin  
- **Fecha de creación:** 07/10/2025

## Instalación

1. Clona el repositorio:  
```bash
git clone https://github.com/Mohamed2651/ValenBici.git
