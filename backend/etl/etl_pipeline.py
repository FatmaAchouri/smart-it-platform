import pandas as pd
from sqlalchemy import create_engine
import os

EXCEL_FILE = 'data/Selecteur_des_equipements-0-33360702.xlsx'

engine = create_engine('mysql+pymysql://root:@localhost/smartitplatform')

def run_etl():
    print("🚀 Démarrage du Pipeline ETL Amélioré...")

    df = pd.read_excel(EXCEL_FILE)
    print(f"✅ {len(df)} lignes chargées")

    # Nettoyage des noms de colonnes
    df.columns = df.columns.str.strip()

    # Mapping des colonnes
    column_mapping = {
        'Code équipement': 'code_equipement',
        'Description de l\'équipement': 'name',
        'État': 'status',
        'Zone': 'zone',
        'Emplacement': 'location',
        'Marque': 'brand',
        'Modèle': 'model',
        'Type': 'type',               # Si tu as une colonne Type
        'Famille': 'famille'
    }

    df = df.rename(columns=column_mapping)

    # Remplissage automatique
    df = df.fillna({
        'status': 'NORMAL',
        'zone': 'Inconnue',
        'location': 'Non défini',
        'brand': 'Inconnu',
        'model': 'Non défini',
        'name': lambda x: f"Machine {x['code_equipement']}" if pd.notnull(x['code_equipement']) else 'Machine sans nom'
    })

    df['health_score'] = 85
    df['failure_risk'] = 15
    df['days_until_maintenance'] = 14

    print("✅ Remplissage automatique terminé")

    # Sauvegarde
    df.to_sql('machine', engine, if_exists='replace', index=False)
    
    print("🎉 Pipeline terminé avec succès !")

if __name__ == "__main__":
    run_etl()