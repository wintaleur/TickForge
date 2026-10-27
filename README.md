# TickForge

## Compilation SCSS / CSS

Le projet utilise Sass pour compiler les fichiers SCSS en CSS, qui sont ensuite utilisés par Vellum GUI,
et Gradle pour intégrer les ressources générées au mod Fabric.

Sources SCSS : src/main/scss/
CSS généré : build/generated/resources/sass/
Ressources HTML : src/main/resources/
Commandes


```bash
npm run sass:build
```
Compile les fichiers SCSS manuellement.


```bash
npm run sass:watch
```
Recompile automatiquement les fichiers SCSS à chaque modification.


```bash
./gradlew build
```
Compile le mod et génère automatiquement le CSS avant l'intégration des ressources dans le JAR.

Prérequis : Node.js, npm et Gradle.