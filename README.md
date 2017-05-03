#DD1349Projekt

**Studenter:** @johvh @davidjo2

**Programmeringspråk:** java

### Projektbeskrivning

**Rocket Krieg** är ett spel där man genom att kontrollera en raket försöker överleva så länge som möjligt i ett universum fylld av faror från alla olika håll och kanter. En blandning av varierande spellägen kommer att vara tillgängliga som erbjuder spännande utmaningar för dig och din kompis att anta. Projektet är konstruerat med hjälp av libGDX, ett spelbibliotek som erbjuder ett flertal olika användbara metoder för denna sortens spel. Rocket Krieg kommer att bestå av drygt 12 klasser där de flesta utgörs av olika spelentiteter såsom asteroider samt alienskepp. 

**Spellägen:**

-Singleplayer survival: Försök överleva så länge som möjligt medans asteroider samt fiendeskepp spawnar runtom dig. Vid kollision förstörs raketen och spelet avslutas. 

-Co-op survival: Två personer spelar tillsammans och har ansvar för varsin kontroll; en person ansvarar för gasen och den andra svänger.  

**Tillfälligt klassdiagram**
![image](https://gits-15.sys.kth.se/storage/user/2798/files/78036ae0-300c-11e7-8599-5ccb28db4e2d)

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

Projektets klasser som inte har med grafiken att göra kommer vi att testa med JUnit eller liknande test-bibliotek. De grafiska klasserna kommer att testas genom att spela spelet och se om objekten interagerar med varandra på korrekt sätt.
