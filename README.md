#DD1349Projekt

**Studenter:** @johvh @davidjo2

**Programmeringspråk:** java

### Projektbeskrivning

**RocketKrieg** är ett spel där man genom att kontrollera en raket försöker överleva så länge som möjligt i ett universum fylld av faror från alla olika håll och kanter. En blandning av varierande spellägen kommer att vara tillgängliga som erbjuder spännande utmaningar för dig och din kompis att anta. Projektet är konstruerat med hjälp av libGDX, ett spelbibliotek som erbjuder ett flertal olika användbara metoder för denna sortens spel.  

**Gamemodes:**

-Singleplayer survival: Försök överleva så länge som möjligt medans asteroider samt fiendeskepp ankommer från runtom dig. Vid kollision förstörs raketen och spelet avslutas. 

-Co-op survival: Två personer spelar tillsammans och har ansvar för varsin kontroll; en person ansvarar för gasen och den andra svänger.  

**Objects:**

-PlayerSpaceShip (Missiles)
-Alien ship
-Asteroids

**Tile (512x512px):**

-Planets
-Black holes
-Structures

### How-to-run
1. Download/clone project from github.
2. Open in intellij på importing the build.gradle file.
3. Edit configurations: 

-> +  -> Application

- name it desktop
- select desktopLauncher as main class.
- Change working directory to assets folder in core.
- Select desktop_main as classpath of module.

### Testningsstrategi

Projektets klasser som inte har med grafiken att göra kommer vi att testa med JUnit eller liknande test-bibliotek. De grafiska klasserna kommer att testas genom att spela spelet och se om objekten interagerar med varandra enligt planen.
