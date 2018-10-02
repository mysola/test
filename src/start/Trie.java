package start;

public class Trie {
    private class TrieNode{
        TrieNode[] childs = new TrieNode[26];
        int count = 0;
        boolean isStr;
    }

    public TrieNode trieInsert(TrieNode trieNode,String s){
        if(s==null||s.length()==0)
            return trieNode;
        TrieNode curNode = trieNode;
        int index = 0;
        for(char ch : s.toCharArray()){
            index = ch-97;
            if(curNode.childs[index]==null){
                curNode.childs[index] = new TrieNode();
            }
            curNode = curNode.childs[index];
            curNode.count++;
        }
        curNode.isStr = true;
        return trieNode;
    }

    public int trieSearch(TrieNode trieNode,String s){
        if(s==null||s.length()==0)
            return 0;
        TrieNode curNode = trieNode;
        int index = 0;
        for(char ch : s.toCharArray()){
            index = ch-97;
            if(curNode.childs[index]==null){
                return 0;
            }
            curNode = curNode.childs[index];
        }
        return curNode.count;
    }

    public TrieNode trieMerge(TrieNode trieNode1,TrieNode trieNode2){
        if(trieNode1==null){
            return trieNode2;
        }
        if(trieNode2==null){
            return trieNode1;
        }
        for(int i = 0;i < 26;i++){
            if(trieNode1.childs[i]==null&&trieNode2.childs[i]==null)
                continue;
            trieNode1.count = trieNode2.count+trieNode1.count;
            trieNode1.isStr = trieNode2.isStr||trieNode1.isStr;
            trieNode1.childs[i] = trieMerge(trieNode1.childs[i],trieNode2.childs[i]);
        }
        return trieNode1;
    }

    public void triePrint(TrieNode trieNode,char[] curChars,int index){
        TrieNode t = null;
        for(int i = 0 ; i < 26 ; i++){
            t = trieNode.childs[i];
            if(t==null)
                continue;
            curChars[index++] = (char)(i+97);
            if(t.isStr)
                System.out.println(new String(curChars,0,index)+","+t.count);
            triePrint(t,curChars,index);
            index--;
        }
    }

    public TrieNode genTrie(String[] strs){
        TrieNode trieNode = new TrieNode();
        for(String s : strs){
            trieNode = trieInsert(trieNode,s);
        }
        return trieNode;
    }

    public int isSubSte(String a,String b){
        String[] suffixArray = new String[a.length()];
        for(int i=0;i<a.length();i++){
            suffixArray[i] = a.substring(i,a.length());
        }
        TrieNode root = genTrie(suffixArray);
        return trieSearch(root,b);
    }
    public static void main(String[] args){
        Trie trie = new Trie();
        System.out.println(trie.isSubSte("banana","nan"));
        System.out.println(trie.isSubSte("banana","na"));
        System.out.println(trie.isSubSte("banana","an"));
        System.out.println(trie.isSubSte("banana","nana"));
        System.out.println(trie.isSubSte("banana","ananaa"));
        System.out.println(trie.isSubSte("banana","a"));
        System.out.println(trie.isSubSte("banana","banana"));
//        String[] strs = {"abc","ab","bd","dda"};
//        TrieNode trieNode = trie.genTrie(strs);
//        trie.triePrint(trieNode,new char[10],0);
//
//        trie.trieSearch(trieNode,"b");
//        trie.trieSearch(trieNode,"bd");
//        trie.trieSearch(trieNode,"bdc");
//
//        Trie trie1 = new Trie();
//        String[] strs1 = {"abd","ac","bcd","ada"};
//        TrieNode trieNode1 = trie1.genTrie(strs1);
//        trie1.triePrint(trieNode1,new char[10],0);
//        System.out.println("--------------");
//        trie1.triePrint(trie1.trieMerge(trieNode1,trieNode),new char[10],0);
    }
}
