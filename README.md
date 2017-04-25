Twixt

D'après le code original de Johannes Schwagereit présent sur http://twixt.wikifoundry.com/page/Do+it+yourself .

Il s'agit d'un readme minimaliste à compléter !

1. Packages nécessaires
   - java
   - ant

2. Compilation
  Pour compiler le programme twixt, il suffit d'exécuter la commande "ant"

3. Exécution
  Deux modes d'exécutions sont disponibles
     - ./MaraTwixtPP.jar 
     - ./MaraTwixtPP.jar <nom classe AI joueur 1> <nom classe AI joueur 2>

  Le second mode est actuellement inutilisable via le jar (voir premier TODO). Il est donc
  préférable d'utiliser pour le moment la commande suivante pour le second mode
  
       bash lancer-partie.sh <nom classe AI joueur 1> <nom classe AI joueur 2>

4. TODO
   * Lister les classes associées aux IA automatiquement dans la factory
   * Prévoir un système de debug/log
   * Génération de stats ?
