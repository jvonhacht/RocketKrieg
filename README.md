# DD1349Projekt
![image](https://gits-15.sys.kth.se/storage/user/2835/files/404c70aa-3012-11e7-9f22-42d7ef0ad55e)

**Studenter:** @johvh @davidjo2

**Nedladdning:** [vSync off](https://goo.gl/x1ptkp)

**Programmeringspråk:** java

### Projektbeskrivning

**Rocket Krieg** är ett spel där man genom att kontrollera en raket försöker överleva så länge som möjligt i ett universum fylld av faror från alla olika håll och kanter. En blandning av varierande spellägen kommer att vara tillgängliga som erbjuder spännande utmaningar för dig och din kompis att anta. Projektet är konstruerat med hjälp av libGDX, ett spelbibliotek som erbjuder ett flertal olika användbara metoder för denna sorts spel. Rocket Krieg består av 22 klasser där de flesta utgörs av olika spelentiteter såsom asteroider samt alienskepp. 

**Tillgängliga spellägen:**

-Singleplayer survival: Försök samla på dig så många poäng som möjligt genom att förstöra fiendeskepp som spawnar runtom dig. Vid kollision förstörs raketen och spelet avslutas. Poäng kan även fås genom att åka över energiringar.

**Tillfälligt klassdiagram**
![image](http://image.prntscr.com/image/ce2182cd518c4584a94ece1210e91a29.png)

### How-to-run

**Intellij**

1. Download/clone project from github.
2. Open in intellij på importing the build.gradle file.
3. Edit configurations: 

-> +  -> Application

- name it desktop
- select desktopLauncher as main class.
- Change working directory to assets folder in core.
- Select desktop_main as classpath of module.
 
**jar**

I windows och OSX är det bara att ladda ner jarfilen och dubbelklicka om man har java intallerat på datorn.

I linux verkar det som att man måste markera jar filen som executable m.h.a. chmod +x filenamn.jar

### Testningsstrategi

Vi har genom projektets gång testat Rocket Krieg själva efter varje förändring för att försäkra oss om att allt funkar som det ska. I samband med stora förändringar lät vi en grupp av speltestare testa på spelet för att kolla huruvida objekten interagerade med varandra på rätt sätt.
