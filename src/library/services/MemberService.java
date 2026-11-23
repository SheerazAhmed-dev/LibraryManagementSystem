
package library.services;

import library.models.Member;
import library.datastructures.DynamicArray;
import library.datastructures.HashTable;

public class MemberService{
    private DynamicArray<Member> members;
    private HashTable<String,Member> memberIndex;

    public MemberService(){
        members = new DynamicArray<>();
        memberIndex = new HashTable<>();
    }

    public void registerMember(Member member){
        if(member == null){
            throw new IllegalArgumentException("Member cannot be null");
        }

        String id = member.getId();

        if(id == null || id.isBlank()){
            throw new IllegalArgumentException("Invalid member ID");
        }

        if(memberIndex.containsKey(id)){
            throw new IllegalArgumentException("Member ID already exists");
        }

        members.add(member);
        memberIndex.put(id,member);
    }

    public Member searchById(String id){
        if(id == null || id.isBlank()){
            return null;
        }

        return memberIndex.get(id.trim());
    }

    public DynamicArray<Member> searchByName(String name){
        DynamicArray<Member> result = new DynamicArray<>();

        if(name == null || name.isBlank()){
            return result;
        }

        for(int i = 0; i < members.size(); i++){
            Member member = members.get(i);

            if(member.getName().equalsIgnoreCase(name.trim())){
                result.add(member);
            }
        }

        return result;
    }

    public void updateMember(String id,String name,String email,String phone){
        Member member = searchById(id);

        if(member == null){
            throw new IllegalArgumentException("Member not found");
        }

        if(name == null || name.isBlank() || email == null || email.isBlank() ||
           phone == null || phone.isBlank()){
            throw new IllegalArgumentException("Member details cannot be empty");
        }

        member.setName(name.trim());
        member.setEmail(email.trim());
        member.setPhone(phone.trim());
    }

    public boolean deactivateMember(String id){
        Member member = searchById(id);

        if(member == null || !member.isActive()){
            return false;
        }

        member.setActiveStatus(false);
        return true;
    }

    public boolean reactivateMember(String id){
        Member member = searchById(id);

        if(member == null || member.isActive()){
            return false;
        }

        member.setActiveStatus(true);
        return true;
    }

    public boolean deleteMember(String id){
        if(id == null || id.isBlank()){
            return false;
        }

        Member member = searchById(id);

        if(member == null){
            return false;
        }

        for(int i = 0; i < members.size(); i++){
            if(members.get(i).getId().equalsIgnoreCase(id.trim())){
                members.remove(i);
                break;
            }
        }

        memberIndex.remove(id.trim());
        return true;
    }

    public DynamicArray<Member> getAllMembers(){
        DynamicArray<Member> result = new DynamicArray<>();

        for(int i = 0; i < members.size(); i++){
            result.add(members.get(i));
        }

        return result;
    }

    public DynamicArray<Member> sortByName(){
        DynamicArray<Member> result = getAllMembers();

        for(int i = 0; i < result.size() - 1; i++){
            for(int j = 0; j < result.size() - i - 1; j++){
                if(result.get(j).getName().compareToIgnoreCase(result.get(j + 1).getName()) > 0){
                    Member temp = result.get(j);
                    result.set(j,result.get(j + 1));
                    result.set(j + 1,temp);
                }
            }
        }

        return result;
    }

    public int getMemberCount(){
        return members.size();
    }

    public boolean isEmpty(){
        return members.isEmpty();
    }

    public void displayAllMembers(){
        if(members.isEmpty()){
            System.out.println("No members registered!");
            return;
        }

        for(int i = 0; i < members.size(); i++){
            members.get(i).displayDetails();
            System.out.println("-------------------------");
        }
    }
}
