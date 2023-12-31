# Epic BnB Backend

Benvenuti nel backend di Epic BnB, il sistema di gestione per il sito di annunci per la prenotazione di vacanze! Qui troverete informazioni importanti riguardanti il funzionamento e l'installazione del backend del progetto.

## Tecnologie Utilizzate

- **Linguaggio di programmazione**: [Java]
- **Ambiente di Sviluppo**: [Eclipse IDE]
- **Framework**: [Spring,Springboot]
- **Database**: [PostgreSQL]

## Prerequisiti

Prima di procedere all'installazione e all'avvio del backend di Epic BnB, assicurati di avere installato quanto segue:

-**Java Development Kit (JDK)** - Versione 8 o superiore.

-**PostgreSQL** - Un database PostgreSQL in cui verranno memorizzati utenti,annunci,prenotazioni e commenti.

## Installazione

1. Clona il repository del backend da GitHub:

https://github.com/VittoriaBasile/EPIC_BNB.git

2. Importa il progetto in Eclipse:

- Apri Eclipse IDE.
- Seleziona "File" > "Import" > "Existing Projects into Workspace".
- Seleziona la cartella del progetto clonato e importalo nel workspace di Eclipse.


## Configurazione

   Creare un file env.properties in cui inserire i tuoi dati(ricorda si inserire il file in gitignore)

URL=postgresql://numero porta database/nome database

PG_USERNAME=username

PG_PASSWORD=password

PG_DB=nome database

PORT=numero porta backend

JWT_SECRET=numero segreto

JWT_EXPIRATION=giorni scadenza token

BCRYPT_SECRET=stringa segreta 

ADMIN_NOME=nome

ADMIN_COGNOME=cognome

ADMIN_USERNAME=username

ADMIN_EMAIL=email
ADMIN_PASSWORD=password
## Documentazione Rest API

Una volta avviato il server, è possibile visualizzare la documentazione delle REST API utilizzando Swagger. Accedi al seguente URL nel tuo browser:

[Documentazione Swagger delle Rest API](http://localhost:3001/swagger-ui/index.html)

## Repository del Front-end

Per il front-end di Epic BnB ho utilizzato

🖥[repository-frontend](https://github.com/VittoriaBasile/progetto-finale-front-end)🖥


## Contattami

Se hai domande, suggerimenti, o hai bisogno di assistenza riguardo a Epic BnB, non esitare a contattarmi!

📧 Email: **vichy.basile@gmail.com**

LinkedIn : **Vittoria Basile**

GitHub : **VittoriaBasile**
