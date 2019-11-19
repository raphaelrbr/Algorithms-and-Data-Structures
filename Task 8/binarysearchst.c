/****************************************************************
    Nome: Raphael Ribeiro da Costa e Silva 
    NUSP: 10281601

    Ao preencher esse cabeçalho com o meu nome e o meu número USP,
    declaro que todas as partes originais desse exercício programa (EP)
    foram desenvolvidas e implementadas por mim e que portanto não 
    constituem desonestidade acadêmica ou plágio.
    Declaro também que sou responsável por todas as cópias desse
    programa e que não distribui ou facilitei a sua distribuição.
    Estou ciente que os casos de plágio e desonestidade acadêmica
    serão tratados segundo os critérios divulgados na página da 
    disciplina.
    Entendo que EPs sem assinatura devem receber nota zero e, ainda
    assim, poderão ser punidos por desonestidade acadêmica.

    Abaixo descreva qualquer ajuda que você recebeu para fazer este
    EP.  Inclua qualquer ajuda recebida por pessoas (inclusive
    monitoras e colegas). Com exceção de material de MAC0323, caso
    você tenha utilizado alguma informação, trecho de código,...
    indique esse fato abaixo para que o seu programa não seja
    considerado plágio ou irregular.

    Exemplo:

        A monitora me explicou que eu devia utilizar a função xyz().

        O meu método xyz() foi baseada na descrição encontrada na 
        página https://www.ime.usp.br/~pf/algoritmos/aulas/enumeracao.html.

    Descrição de ajuda ou indicação de fonte:



    Se for o caso, descreva a seguir 'bugs' e limitações do seu programa:






/*
 * MAC0323 Estruturas de Dados e Algoritmo II
 * 
 * Tabela de simbolos implementada atraves de vetores ordenados 
 * redeminsionaveis 
 *
 *     https://algs4.cs.princeton.edu/31elementary/BinarySearchST.java.html
 * 
 * As chaves e valores desta implementação são mais ou menos
 * genéricos
 */

/* interface para o uso da funcao deste módulo */
#include "binarysearchst.h"  

#include <stdlib.h>  /* free() */
#include <string.h>  /* memcpy() */
#include "util.h"    /* emalloc(), ecalloc() */

#undef DEBUG
#ifdef DEBUG
#include <stdio.h>   /* printf(): para debug */
#endif

/*
 * CONSTANTES 
 */

/*------------------------------------------------------------*/
/* 
 * Funções administrativas
 */

static void resize(BinarySearchST st, int newCapacity);


/*----------------------------------------------------------*/
/* 
 * Estrutura Básica da Tabela de Símbolos: 
 * 
 * implementação com vetores ordenados
 */


struct binarySearchST {
  int n; //Numero de elementos na arvore
  int capacity; //Capacidade máxima da arvore
  void ** keys; //Array de chaves
  void ** vals; //Array de valores
  size_t * nKeys; //Array com tamanho das keys
  size_t * nVals; //Array com tamanho dos valores
  int (*compar)(const void*,const void*); //Função de comparacao
  int iter; //Ponteiro para iteracao
};

/*-----------------------------------------------------------*/
/*
 *  initST(COMPAR)
 *
 *  RECEBE uma função COMPAR() para comparar chaves.
 *  RETORNA (referência/ponteiro para) uma tabela de símbolos vazia.
 *
 *  É esperado que COMPAR() tenha o seguinte comportamento:
 *
 *      COMPAR(key1, key2) retorna um inteiro < 0 se key1 <  key2
 *      COMPAR(key1, key2) retorna 0              se key1 == key2
 *      COMPAR(key1, key2) retorna um inteiro > 0 se key1 >  key2
 * 
 *  TODAS OS OPERAÇÕES da ST criada utilizam a COMPAR() para comparar
 *  chaves.
 * 
 */
BinarySearchST
initST(int (*compar)(const void *key1, const void *key2))
{
    BinarySearchST nova = emalloc(sizeof(struct binarySearchST));
    nova->n = 0;
    nova->compar = compar;
    nova->keys = emalloc(sizeof(void*)*8);
    nova->vals = emalloc(sizeof(void*)*8);
    nova->nKeys = emalloc(sizeof(size_t)*8);
    nova->nVals = emalloc(sizeof(size_t)*8);
    nova->capacity = 8;
    return nova;
}

/*-----------------------------------------------------------*/
/*
 *  freeST(ST)
 *
 *  RECEBE uma BinarySearchST  ST e devolve ao sistema toda a memoria 
 *  utilizada por ST.
 *
 */
void  
freeST(BinarySearchST st)
{
    if(st!=NULL){
        for(int i = 0; i<size(st); i++){
            free(st->keys[i]);
            free(st->vals[i]);
        }
        free(st);
    }
}    

/*------------------------------------------------------------*/
/*
 * OPERAÇÕES USUAIS: put(), get(), contains(), delete(),
 * size() e isEmpty().
 */

/*-----------------------------------------------------------*/
/*
 *  put(ST, KEY, NKEY, VAL, NVAL)
 * 
 *  RECEBE a tabela de símbolos ST e um par KEY-VAL e procura a KEY na ST.
 *
 *     - se VAL é NULL, a entrada da chave KEY é removida da ST  
 *  
 *     - se KEY nao e' encontrada: o par KEY-VAL é inserido na ST
 *
 *     - se KEY e' encontra: o valor correspondente é atualizado
 *
 *  NKEY é o número de bytes de KEY e NVAL é o número de bytes de NVAL.
 *
 *  Para criar uma copia/clone de KEY é usado o seu número de bytes NKEY.
 *  Para criar uma copia/clode de VAL é usado o seu número de bytes NVAL.
 *
 */
void  
put(BinarySearchST st, const void *key, size_t nKey, const void *val, size_t nVal)
{
    if(val == NULL) {
        delete(st,key);
        return;
    }

    int i = rank(st,key);


    void * valClone = emalloc(nVal);
    memcpy(valClone, val, nVal);
    

    //Chave esta na ST
    if(i<(st->n) && (st->compar)(st->keys[i],key)==0){
        st->vals[i] = valClone;
        st->nVals[i] = nVal;
        return;
    }

    //Insere novo par chave-valor na ST
    if(st->n == st->capacity) resize(st,2*st->capacity);

    for(int j = (st->n); j>i; j--) {
        st->keys[j] = st->keys[j-1];
        st->nKeys[j] = st->nKeys[j-1];
        st->vals[j] = st->vals[j-1];
        st->nVals[j] = st->nVals[j-1];
    }


    void * keyClone = emalloc(nKey);
    memcpy(keyClone, key, nKey);


    st->keys[i] = keyClone;
    st->vals[i] = valClone;
    st->nKeys[i] = nKey;
    
    st->nVals[i] = nVal;

    (st->n)++;    
}    

/*-----------------------------------------------------------*/
/*
 *  get(ST, KEY)
 *
 *  RECEBE uma tabela de símbolos ST e uma chave KEY.
 *
 *     - se KEY está em ST, RETORNA NULL;
 *
 *     - se KEY não está em ST, RETORNA uma cópia/clone do valor
 *       associado a KEY.
 * 
 */
void *
get(BinarySearchST st, const void *key)
{
    if(isEmpty(st)) return NULL;

    int i = rank(st,key);
    if(i<(st->n) && (st->compar(st->keys[i],key)) == 0){
        void * valClone = emalloc(st->nVals[i]);
        memcpy(valClone,st->vals[i],st->nVals[i]);
        return valClone;
    }
    return NULL;
}

/*-----------------------------------------------------------*/
/* 
 *  CONTAINS(ST, KEY)
 *
 *  RECEBE uma tabela de símbolos ST e uma chave KEY.
 * 
 *  RETORNA TRUE se KEY está na ST e FALSE em caso contrário.
 *
 */
Bool
contains(BinarySearchST st, const void *key)
{
    return get(st,key) != NULL;
}

/*-----------------------------------------------------------*/
/* 
 *  DELETE(ST, KEY)
 *
 *  RECEBE uma tabela de símbolos ST e uma chave KEY.
 * 
 *  Se KEY está em ST, remove a entrada correspondente a KEY.
 *  Se KEY não está em ST, faz nada.
 *
 */

void
delete(BinarySearchST st, const void *key)
{
    if (isEmpty(st)) return;

    int i = rank(st,key);

    if(i == st->n || st->compar(st->keys[i],key)!=0) return;

    free(st->keys[i]);
    free(st->vals[i]);

    for(int j = i; j<(st->n)-1; j++){
        st->keys[j] = st->keys[j+1];
        st->nKeys[j] = st->nKeys[j+1];
        st->vals[j] = st->vals[j+1];
        st->nVals[j] = st->nVals[j+1];
    }

    (st->n)--;
    int n = st->n;
    st->keys[n] = NULL;
    st->vals[n] = NULL;

    if(n > 0 && n==st->capacity/4) resize(st,st->capacity/2);

}

/*-----------------------------------------------------------*/
/* 
 *  SIZE(ST)
 *
 *  RECEBE uma tabela de símbolos ST.
 * 
 *  RETORNA o número de itens (= pares chave-valor) na ST.
 *
 */
int
size(BinarySearchST st)
{
    return st->n;
}

/*-----------------------------------------------------------*/
/* 
 *  ISEMPTY(ST, KEY)
 *
 *  RECEBE uma tabela de símbolos ST.
 * 
 *  RETORNA TRUE se ST está vazia e FALSE em caso contrário.
 *
 */
Bool
isEmpty(BinarySearchST st)
{   
    return st->n == 0;
}


/*------------------------------------------------------------*/
/*
 * OPERAÇÕES PARA TABELAS DE SÍMBOLOS ORDENADAS: 
 * min(), max(), rank(), select(), deleteMin() e deleteMax().
 */

/*-----------------------------------------------------------*/
/*
 *  MIN(ST)
 * 
 *  RECEBE uma tabela de símbolos ST e RETORNA uma cópia/clone
 *  da menor chave na tabela.
 *
 *  Se ST está vazia RETORNA NULL.
 *
 */
void *
min(BinarySearchST st)
{   
    if(st->n == 0) return NULL;
    void * keyClone = emalloc(st->nKeys[0]);
    memcpy(keyClone,st->keys[0],st->nKeys[0]);
    return keyClone;
}

/*-----------------------------------------------------------*/
/*
 *  MAX(ST)
 * 
 *  RECEBE uma tabela de símbolos ST e RETORNA uma cópia/clone
 *  da maior chave na tabela.
 *
 *  Se ST está vazia RETORNA NULL.
 *
 */
void *
max(BinarySearchST st)
{
    if(st->n == 0) return NULL;
    int n = st->n;
    void * keyClone = emalloc(st->nKeys[n-1]);
    memcpy(keyClone,st->keys[n-1],st->nKeys[n-1]);
    return keyClone;
}

/*-----------------------------------------------------------*/
/*
 *  RANK(ST, KEY)
 * 
 *  RECEBE uma tabela de símbolos ST e uma chave KEY.
 *  RETORNA o número de chaves em ST menores que KEY.
 *
 *  Se ST está vazia EXIT_FAILURE.
 *
 */


int
rank(BinarySearchST st, const void *key)
{
    if(st->n == 0) EXIT_FAILURE;
    int lo = 0, hi=(st->n)-1;
    while(lo<=hi) {
        int mid = lo + (hi-lo) / 2;
        int cmp = st->compar(key,st->keys[mid]);
        if( cmp < 0) hi = mid - 1;
        else if(cmp > 0) lo = mid + 1;
        else return mid;
    }
    return lo;
} 

/*-----------------------------------------------------------*/
/*
 *  SELECT(ST, K)
 * 
 *  RECEBE uma tabela de símbolos ST e um inteiro K >= 0.
 *  RETORNA a (K+1)-ésima menor chave da tabela ST.
 *
 *  Se ST não tem K+1 elementos RETORNA NULL.
 *
 */
void *
select(BinarySearchST st, int k)
{
    if(k<0 || k>=size(st)) return NULL;
    void * keyClone = emalloc(st->nKeys[k]);
    memcpy(keyClone,st->keys[k],st->nKeys[k]);
    return keyClone;
}

/*-----------------------------------------------------------*/
/*
 *  deleteMIN(ST)
 * 
 *  RECEBE uma tabela de símbolos ST e remove a entrada correspondente
 *  à menor chave.
 *
 *  Se ST está vazia, faz nada.
 *
 */
void
deleteMin(BinarySearchST st)
{
    delete(st,min(st));
}

/*-----------------------------------------------------------*/
/*
 *  deleteMAX(ST)
 * 
 *  RECEBE uma tabela de símbolos ST e remove a entrada correspondente
 *  à maior chave.
 *
 *  Se ST está vazia, faz nada.
 *
 */
void
deleteMax(BinarySearchST st)
{
    delete(st,max(st));
}

/*-----------------------------------------------------------*/
/* 
 *  KEYS(ST, INIT)
 * 
 *  RECEBE uma tabela de símbolos ST e um Bool INIT.
 *
 *  Se INIT é TRUE, KEYS() RETORNA uma cópia/clone da menor chave na ST.
 *  Se INIT é FALSE, KEYS() RETORNA uma cópia/clone da chave sucessora da última chave retornada.
 *  Se ST está vazia ou não há sucessora da última chave retornada, KEYS() RETORNA NULL.
 *
 *  Se entre duas chamadas de KEYS() a ST é alterada, o comportamento é 
 *  indefinido. 
 *  
 */
void * 
keys(BinarySearchST st, Bool init)
{
    if(init) st->iter = 0;
    if(st->n == 0 || st->iter == st->n) return NULL;
    void * keyClone = emalloc(st->nKeys[st->iter]);
    memcpy(keyClone,st->keys[st->iter],st->nKeys[st->iter]);
    (st->iter)++;
    return keyClone;
}

/*-----------------------------------------------------------*/
/*
  Visit each entry on the ST.

  The VISIT function is called, in-order, with each pair key-value in the ST.
  If the VISIT function returns zero, then the iteration stops.

  visitST returns zero if the iteration was stopped by the visit function,
  nonzero otherwise.
*/
int
visitST(BinarySearchST st, int (*visit)(const void *key, const void *val))
{
    for(int i = 0; i<st->n; i++){
        int r = (*visit)(st->keys[i],st->vals[i]);
        if(r==0) return r;
    }
    return 1;
}
    

/*------------------------------------------------------------*/
/* 
 * Funções administrativas
 */

static void resize(BinarySearchST st, int newCapacity){
    st->keys = realloc(st->keys, newCapacity*sizeof(void*));
    st->vals = realloc(st->vals,newCapacity*sizeof(void*));
    st->nKeys = realloc(st->nKeys,newCapacity*sizeof(size_t));
    st->nVals =  realloc(st->nVals,newCapacity*sizeof(size_t));
    st->capacity = newCapacity;
}









