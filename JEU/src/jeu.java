import extensions.CSVFile;
import extensions.File;
class Monko extends Program{
    
    final String titre = "../ressources/ascii/titre.txt";
    final String regle = "../ressources/ascii/monko_regles.txt";
    final String personnage = "../ressources/ascii/personnage.txt";
    final String pret = "../ressources/ascii/pret.txt";
    final String niveau = "../ressources/ascii/niveau.txt";
    final String joueur_csv = "../ressources/csv/Joueur.csv";
    private static final char[] MY_ARRAY = {'1','2','3','4','5','6','7','8','9'};
    char caractere ='/';
    String mode ="";
    void affichertexte(String file){
        File fichier = newFile(file);
        while(ready(fichier)){
          String ligne = readLine(fichier);
          println(ligne);
        }
    }
    Joueur newJoueur1(){
        Joueur j1 = new Joueur();
        j1.nom="";
        j1.prenom="";
        j1.classe="";
        j1.score=0;
        j1.xcoordonnee=0;
        j1.ycoordonnee=0;
        return j1;
    }
    Joueur newJoueur(String nom ,String prenom, String classe ,int score,int ycoordonnee, int xcoordonnee){
        Joueur j1 = new Joueur();
        j1.nom= nom;
        j1.prenom= prenom;
        j1.classe=classe;
        j1.score=score;
        j1.ycoordonnee = ycoordonnee;
        j1.xcoordonnee =xcoordonnee;
        return j1;
    }
    
    String[][] load(String nomFichier){
        CSVFile fichier = loadCSV(joueur_csv);
        int ligne = rowCount(fichier);
        int colonne  = columnCount(fichier);
        String[][] joueurs = new String[ligne][colonne];
        joueurs[0][0] = "nom";
        joueurs[0][1] = "prenom";
        joueurs[0][2] = "classe";
        joueurs[0][3] = "score";
        joueurs[0][4] = "xcoordonnee";
        joueurs[0][5] = "ycoordonnee";

        for(int y=1;y<ligne;y++){
            joueurs[y][0] = getCell(fichier, y, 0);
            joueurs[y][1] = getCell(fichier, y, 1);
            joueurs[y][2] = getCell(fichier, y, 2);
            joueurs[y][3] = getCell(fichier, y, 3);
            joueurs[y][4] = getCell(fichier, y, 4);
            joueurs[y][5] = getCell(fichier, y, 5);
        }
        return joueurs;
    }
    void save(Joueur joueur, String[][] current_csv, String nomFichier){
        int nb_col = 6;
        int lig = length(current_csv, 1);
        String[][] new_joueurs = new String[lig+1][nb_col];

        for(int i = 0; i < lig; i++) {
            for(int j = 0; j < nb_col; j++) {
                new_joueurs[i][j] = current_csv[i][j];
            }
        }
        new_joueurs[lig][0]=joueur.nom;
        new_joueurs[lig][1]=joueur.prenom;
        new_joueurs[lig][2]=joueur.classe;
        new_joueurs[lig][3]="" + joueur.score;
        new_joueurs[lig][4]="" + joueur.xcoordonnee;
        new_joueurs[lig][5]="" + joueur.ycoordonnee;
        
        saveCSV(new_joueurs ,nomFichier);
    }
    void chargezjoueur(Joueur joueur, String[][] current_csv, String nomFichier,int pos){
        int nb_col = 6;
        int lig = length(current_csv, 1);
        String[][] new_joueurs = new String[lig][nb_col];

        for(int i = 0; i < lig; i++) {
            for(int j = 0; j < nb_col; j++) {
                new_joueurs[i][j] = current_csv[i][j];
            }
        }
        new_joueurs[pos][0]=joueur.nom;
        new_joueurs[pos][1]=joueur.prenom;
        new_joueurs[pos][2]=joueur.classe;
        new_joueurs[pos][3]="" + joueur.score;
        new_joueurs[pos][4]="" + joueur.xcoordonnee;
        new_joueurs[pos][5]="" + joueur.ycoordonnee;
        
        saveCSV(new_joueurs ,nomFichier);
    }

     char [] [] creermap(int nb){
        char [][] tab = new char [nb][nb];
        return tab;
    }

    void affichage(char [][] tab){
        for(int x=0;x<length(tab,1);x++){
            for(int y=0;y<length(tab,2);y++){
               char a = tab[x][y];
               print(a+" ");
        }
        println();
        }
    }

    char [][] placer_0(char [][] tab){
        for(int i =0; i<length(tab);i+=1){
            for(int j =0; j<length(tab);j+=1){
                tab[i][j]='0';}}
        return tab;
    }

    char [][] placer_ALEATOIREMENT(char [][] tab,int niveau){
            int accumulateur = 0;
            for(int a =0; a<length(tab)-1;a+=1){
                for(int b =0; b<length(tab)-1;b+=1){
                    if(accumulateur<niveau){
                        if(random()<0.05 && a+b!=0 && tab[a][b]=='0'){
                            tab[a][b] = MY_ARRAY[(int)((random()*9))];
                            accumulateur+=1;}
                    }}}




            if(accumulateur<niveau){
                return placer_ALEATOIREMENT(tab,niveau -accumulateur);
            }

            return tab;
        }

   void EstValeurCorrecte(String reponse){
    String res = "";
    int acc=0;
    do{
            if(acc>0){
            println("veuillez entrer une valeur correcte"); 
            }
            res=readString();
            acc+=1;
    }while((!equals(res,reponse)));
    }

String EstEntierCorrecte(int min,int max){

    int acc=0;
    boolean correcte =false;
    String res = "";
    do{
            if(acc>0){
            println("veuillez entrer une valeur correcte"); 
            }
            res=readString();
            for(int i =0;i<length(res);i+=1){
                if((int) (charAt(res,i)-'0')>=min && (int) (charAt(res,i)-'0')<=max){
                    correcte = true;
                }else{
                    correcte = false;
                    break;
                }}
            acc+=1;

            }while(correcte==false);
    return res;
}
    
    
    
   void deplacement(Joueur j1,char direction,int dimensionMap){
        if(direction =='q'){
            if(j1.ycoordonnee==0){
                println("le deplacement n'est pas possibble");}
            
            else{
                j1.ycoordonnee-=1;
                }
        }
        
        
        if(direction =='s'){
            if(j1.xcoordonnee==dimensionMap-1){
                println("le deplacement n'est pas possibble");}
            else{
                j1.xcoordonnee+=1;
            }
        }
        
        
        if(direction =='z'){
            if(j1.xcoordonnee==0){
                println("le deplacement n'est pas possibble");}
            else{
                j1.xcoordonnee-=1;
            }
        }
        
        
        if(direction =='d'){
            if(j1.ycoordonnee==dimensionMap-1){
                println("le deplacement n'est pas possibble");}
            else{
                j1.ycoordonnee+=1;
            }
        }
    }

     int scoreMaj(Joueur j1,char [][] tab){
        if(tab[j1.xcoordonnee][j1.ycoordonnee]>0){
            j1.score += (int) (tab[j1.xcoordonnee] [j1.ycoordonnee] - '0');
        }
        return j1.score;
     }

     int score_a_atteindre(char [][]tab){
        int somme= 0;
        for(int i =0;i<length(tab,1);i+=1){
            for(int y =0;y<length(tab,2);y+=1){
                if(tab[i][y]>0){
                    if(random()>0.5){
                        somme+=  (tab[i][y])-'0';
                    }
                }   
            }
        }
        if(somme==0){
            return score_a_atteindre(tab);
        }
        
        return somme;
        }
    
     int scoreMax(char [][]tab){
        int somme= 0;
        for(int i =0;i<length(tab,1);i+=1){
            for(int y =0;y<length(tab,2);y+=1){
                if(tab[i][y]>0){
                    somme = somme + (tab[i][y]-'0');
                }   
            }
        }
        
        
        return somme;
        }

     char [][] creationTabVide(int dimensionMap , char caractere){
        char [][] tab = new char [dimensionMap][dimensionMap];
        for(int i =0;i<dimensionMap;i+=1){
            for(int y =0;y<dimensionMap;y+=1){
                tab[i][y]=caractere;

          

            }
        } 
        tab[0][0]='J';
        return tab; 
     }
       char [][] tabperso(int dimensionMap,char caractere){
        char [][] tab = new char [dimensionMap][dimensionMap];
        for(int i =0;i<dimensionMap;i+=1){
            for(int y =0;y<dimensionMap;y+=1){
                tab[i][y]=caractere;

                

            }
        } 
        tab[0][0]='J';
        return tab; 
     }
      char [][] deplacement_remise_a_0(char [][] tab, char direction, Joueur j1,int dimensionMap){
        for(int i =0;i<dimensionMap;i+=1){
            for(int y =0;y<dimensionMap;y+=1){
                if(tab[i][y]=='J'){
                    tab[i][y]= '0';
                }

            }}
        tab[j1.xcoordonnee][j1.ycoordonnee]='J';
        return tab;
     
     
      
     }  
   char [][] deplacementTabVide(char [][] tab, char direction, Joueur j1,int dimensionMap,char[][]tab2){
        for(int i =0;i<dimensionMap;i+=1){
            for(int y =0;y<dimensionMap;y+=1){
                if(tab[i][y]=='J'){
                    tab[i][y]= tab2[i][y];
                }

            }}
        tab[j1.xcoordonnee][j1.ycoordonnee]='J';
        return tab;
   }

     void toChar(char [][]tab){
        for(int i =0;i<length(tab,1);i+=1){
            println();
            for(int y =0;y<length(tab,2);y+=1){
                print(tab[i][y]);
                print(' ');
            }
        }
     }

    String lire(){
        String res ="";
        do{
            res=readString();
        }while((equals(res,"")));
        return res;
      }
    
    String liremode(){
        String res ="";
        do{
            res=readString();
        }while((equals(res,"")) || !(stringToInt(res)>0 && stringToInt(res)<4));
        return res;
      }
      char lirechar(){
        char res =' ';
        res=readChar();
        while((res!='z') && (res!='s') && (res!='d') && (res!='q')){
            println("écrire un deplacement correct");
            res=readChar();
        }
        return res;
      }
      


    String gagner(  Joueur j1 , int somme){
        String res = "";
        if(j1.score>somme){
            res = "dommage";
        }
        if(j1.score<somme){
            res="continue";
        }
        else{
            res ="Gagné";
        }
        return res;
    }
    void affichagetableau(String[][] tab){
        for(int i =0; i<(length(tab,1));i+=1){
            for(int j =0; j<length(tab,2);j+=1){
                print(tab[i][j]+" ");
            }
            println();
        }
     }
     Joueur charger(String nomcsv, String[][] tab, Joueur j1){
        int i = 0;
        int pos = -1;
        while( i<length(tab,1) && pos == -1 ){
                if(equals(tab[i][0],nomcsv)){
                    pos=i;
                    }
                i+=1;
            }
        if (pos>-1){
                j1 = newJoueur(tab[pos][0] ,tab[pos][1], tab[pos][2] ,stringToInt(tab[pos][3]),0, 0);
        }else{
            println("nom present dans la liste stp");
            return charger(lire(),tab,j1);
        }
        return j1;
     }
     void mettreajour(Joueur j1 , String[][] current_csv, String nomFichier,int numero){
        int i = 0;
        int pos = -1;
        j1.xcoordonnee = 0;
        j1.ycoordonnee = 0;
        while( i<length(current_csv,1) && pos == -1 ){
                if(equals(current_csv[i][0],j1.nom)){
                    pos=i;
                    }
                i+=1;
            }
            if(numero==1){
                     save(j1, load(joueur_csv), joueur_csv);   
                
            }if(numero==2){
                   chargezjoueur(j1,  current_csv, nomFichier, pos);
                
            }
     }
     
    
    Joueur menu(Joueur j1){
        affichertexte(titre);
        mode = liremode();
        String[][] tab = load(joueur_csv);
        if(equals(mode,"1")){
            clearScreen();
            affichertexte(regle);
            println("nom:");
            String nom= lire();
            println("prénom:");
            String prenom= lire();
            j1 = newJoueur(nom,prenom,"a" ,0,0,0);
        }
        if(equals(mode,"2")){
            affichagetableau(tab);
            println("nom:");
            String nomcsv =lire();
            j1=charger(nomcsv,tab,j1);
            
        }if(equals(mode,"3")){
            println("choisissez  un caractere pour le mode personnalisation ");
            caractere= readChar();
            println("revenir au menu oui/non");
            String reponse = readString();
            while(!equals(reponse,"oui")){
                reponse = readString();
            }
            if(equals(reponse,"oui")){
                return menu(j1); 
            }
        }
        return j1;
    }
    
    void algorithm(){
        Joueur j1 = newJoueur1() ;
        j1 = menu(j1);
        String[][] tab = load(joueur_csv);
        println("taille de la map ?(plus grand que 1)");
        int dimensionMap = readInt();
        println("Nombre d'items present");
        int item = readInt();
        char [][] map= (creermap(dimensionMap));
        char [][] map0 = placer_0(map);
        char [] [] mapitem = (placer_ALEATOIREMENT(map0,item));
        int score_du_joueur = j1.score;
        j1.score =0;
        affichertexte(niveau);
        String niv = liremode();
        if(equals(niv,"1")){
            int score = scoreMax(mapitem);
            println();
            affichertexte(pret);
            String reponse = "non";
            while(!equals(reponse,"oui")){
                reponse = readString();
            }
            println("bonne chance");
            affichage(mapitem);
            delay(5000);
            clearScreen();
            char [][] tabVide= creationTabVide(dimensionMap,caractere);
            toChar(tabperso(dimensionMap,caractere ));
            double nb_depla = (int)(dimensionMap*1.5)*item;
            while(j1.score!=score && nb_depla !=0 ){
                char direction = lirechar(); 
                deplacement(j1,direction,dimensionMap);
                clearScreen();
                toChar(deplacement_remise_a_0(tabVide,direction,j1,dimensionMap));
                scoreMaj(j1,mapitem);
                println();
                println(j1.score+" "+score);
                println(gagner(j1,score));
                nb_depla -=1;
                println("il vous reste "+ nb_depla +" deplacment");
                  
        }
        } 
        if(equals(niv,"2")){
            int score = (score_a_atteindre(mapitem));
            println();
            affichertexte(pret);
            String reponse = "non";
            while(!equals(reponse,"oui")){
                reponse = readString();
            }
            println("bonne chance");
            println("Le résultat à atteindre est "+ score);
            affichage(mapitem);
            delay(5000);
            clearScreen();
            char [][] tabVide= creationTabVide(dimensionMap,caractere);
            toChar(tabVide);
            while(j1.score <score){
                char direction = lirechar(); 
                deplacement(j1,direction,dimensionMap);
                clearScreen();
                toChar(deplacementTabVide(tabVide,direction,j1,dimensionMap,mapitem));
                println();
                println(scoreMaj(j1,mapitem));
                println(j1.score);
                println(gagner(j1,score));
                  
            }
        } 
    mettreajour(j1 , tab, joueur_csv,stringToInt(mode));
    }
}

        
        


