# Smart IT Platform

**Plateforme de gestion et de maintenance prédictive des machines industrielles**  
Développée dans le cadre d’un stage chez **OneTech Company – Tunisie Câbles**

---

## Description

Smart IT Platform est une application web destinée aux techniciens et opérateurs de Tunisie Câbles.  
Elle permet de :

- Gérer l’inventaire des machines industrielles
- Suivre l’état de santé des équipements (Health Score)
- Anticiper les pannes grâce à la maintenance prédictive
- Importer et nettoyer les données depuis des fichiers Excel (Coswin) via un pipeline ETL
- Visualiser les indicateurs à travers des tableaux de bord interactifs
- Consulter l’historique des machines

---

## Fonctionnalités principales

- Gestion des machines (CRUD)
- Calcul automatique du **Health Score** et du **risque de panne**
- Historisation des changements d’état des machines
- Pipeline ETL (Python + Pandas) pour l’importation des données Coswin
- Tableaux de bord avec statistiques et graphiques
- Système de tickets de maintenance
- Interface simple et adaptée aux techniciens

---

## Stack Technique

| Couche              | Technologie                  |
|---------------------|------------------------------|
| Backend             | Spring Boot 3                |
| Base de données     | MySQL                        |
| Frontend            | Thymeleaf + Bootstrap 5      |
| ETL                 | Python (Pandas + SQLAlchemy) |
| Visualisation       | Chart.js                     |
| Sécurité            | Spring Security              |

---

## Structure du projet
smart-it-platform/
├── backend/          # Application Spring Boot
├── frontend/         # (si présent)
└── etl/              # Scripts Python d’importation des données
## Objectif

Ce projet vise à moderniser la gestion des équipements industriels en transformant les données brutes (Excel Coswin) en informations actionnables pour les équipes de maintenance, afin de réduire les pannes imprévues et améliorer la disponibilité des machines.

---

## Auteur

**Fatma Achouri**  
Stage Data Engineering – OneTech Company / Tunisie Câbles
