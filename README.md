# LightBrowser

Un navegador Android ligero y rápido con soporte para extensiones JavaScript.

## Características

✨ **Navegador Ligero**: Basado en WebView para máximo rendimiento
🔌 **Extensiones**: Añade y gestiona extensiones JavaScript
⚙️ **Configuración**: Controla qué extensiones están activas
🌐 **Soporte Web**: Compatible con JavaScript, almacenamiento DOM y bases de datos
📱 **Interfaz Simple**: Diseño minimalista e intuitivo

## Requisitos

- Android 7.0 (API 24) o superior
- Android Studio 2024.1 o superior
- Java 17

## Instalación

### Clonar el repositorio

```bash
git clone https://github.com/PeruxD/light-browser-android.git
cd light-browser-android
```

### Abrir en Android Studio

1. File → Open → Selecciona la carpeta del proyecto
2. Espera a que Gradle sincronice
3. Build → Make Project

## Compilar APK

```bash
# APK de depuración
./gradlew assembleDebug

# APK de producción
./gradlew assembleRelease
```

## Estructura del Proyecto

```
light-browser-android/
├── app/
│   ├── src/main/
│   │   ├── java/com/example/lightbrowser/
│   │   │   ├── MainActivity.kt         # Pantalla principal
│   │   │   ├── BrowserActivity.kt      # Navegador
│   │   │   ├── Extension.kt            # Modelo de extensión
│   │   │   ├── ExtensionManager.kt     # Gestor de extensiones
│   │   │   ├── BrowserPreferences.kt   # Almacenamiento
│   │   │   └── ExtensionAdapter.kt     # Adaptador RecyclerView
│   │   ├── res/
│   │   │   ├── layout/                 # Layouts XML
│   │   │   └── values/                 # Strings, colors, themes
│   │   └── AndroidManifest.xml
│   └── build.gradle
├── build.gradle
├── settings.gradle
└── README.md
```

## Cómo Usar

### Navegar

1. Escribe una URL en el campo de búsqueda
2. Presiona "Ir" o abre directamente desde el navegador
3. Usa los botones de navegación (←, →, Recargar)

### Extensiones

1. En la pantalla principal, verás las extensiones actuales
2. Presiona "Añadir" para crear una nueva extensión
3. Usa el toggle para activar/desactivar extensiones
4. Presiona × para eliminar una extensión

### Ejemplo de Extensión

Una extensión es un script JavaScript que se ejecuta en cada página. Por ejemplo, un bloqueador de anuncios:

```javascript
(() => {
    const removeAds = () => {
        document.querySelectorAll('[id*="ad"], [class*="ad"]').forEach(el => el.style.display = 'none');
    };
    setInterval(removeAds, 1000);
})();
```

## Permisos

- `INTERNET`: Para acceder a sitios web
- `ACCESS_NETWORK_STATE`: Para verificar conexión

## Rendimiento

- **Tamaño APK**: ~5-8 MB
- **RAM Base**: ~50 MB
- **Almacenamiento**: Configurable según sitios visitados

## Limitaciones

- No soporta extensiones de Chrome nativas (usa inyección JavaScript)
- No incluye gestor de descargas avanzado
- Las extensiones están limitadas al sandbox del WebView

## Próximas Características

- [ ] Pestañas múltiples
- [ ] Historial de navegación
- [ ] Favoritos
- [ ] Modo oscuro
- [ ] Gestor de descargas
- [ ] Sincronización de extensiones en la nube

## Licencia

MIT License

## Contribuir

Las contribuciones son bienvenidas. Por favor:

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## Soporte

Para reportar bugs o sugerir características, abre un issue en GitHub.
