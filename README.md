# Systeme distribuee : programmationReseaux

Ce dépôt contient une application client-serveur de chat qui utilise deux modèles différents pour la communication : le modèle multi-threads avec E/S bloquantes (java.io) et le modèle single thread avec E/S non bloquantes (java.nio).

## Modèle Multi Threads Blocking IO (java.io)
Ce modèle utilise le package java.io pour mettre en œuvre un serveur de chat multi-threads. Voici les étapes pour exécuter l'application :

1- Développement du serveur de chat multi-threads :
  Implémentez la classe ChatServer en utilisant java.io pour gérer la communication avec les clients.
  Gérez les connexions simultanées des clients en utilisant un modèle multi-threads.

2- Test du serveur avec un client Telnet :
  Exécutez le serveur de chat.
  Ouvrez une fenêtre de terminal et utilisez la commande telnet pour vous connecter au serveur.
  Testez la communication en envoyant des messages depuis le client Telnet.

3- Création d'un client Java avec une interface graphique JavaFX :
  Implémentez une interface graphique en utilisant JavaFX pour permettre aux utilisateurs d'envoyer et de recevoir des messages.
  Utilisez java.io pour communiquer avec le serveur de chat.

4- Création d'un client Python ou d'un autre langage :
  Implémentez un client dans un langage de votre choix en utilisant java.io pour communiquer avec le serveur de chat.
  
 ## Modèle Single Thread avec Non Blocking IO (java.nio)

Ce modèle utilise le package java.nio pour mettre en œuvre un serveur de chat single thread avec des E/S non bloquantes. Suivez ces étapes pour exécuter l'application :

1- Développement du serveur de chat single thread avec des E/S non bloquantes :
   Implémentez la classe NonBlockingChatServer en utilisant java.nio pour gérer la communication avec les clients.
   Utilisez les Selectors et les Channels de java.nio pour gérer les opérations d'E/S non bloquantes.
   
2- Test du serveur avec un client Telnet, un client Java et un client dans un autre langage :
  Exécutez le serveur de chat non bloquant.
  Utilisez les clients Telnet, Java et d'autres langages pour vous connecter au serveur et envoyer/recevoir des messages.
  Vérifiez que le serveur traite les opérations d'E/S de manière non bloquante.
  
 ## Utilisation de JMeter pour tester les performances des serveurs
 
 Utilisez l'outil JMeter pour tester les performances des deux serveurs de chat. Suivez les étapes ci-dessous :

1- Téléchargez et installez Apache JMeter (https://jmeter.apache.org/).
2- Configurez JMeter pour envoyer des requêtes aux serveurs de chat en utilisant le protocole approprié (HTTP ou autre) et les paramètres nécessaires.
3- Exécutez les tests de performance pour évaluer les performances des serveurs en termes de capacité, de latence, de temps de réponse, etc.
