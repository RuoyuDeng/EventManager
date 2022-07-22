public class VIP {
    private String aName;

    public VIP(String pName){
        aName = pName;

    }

    public String getaName() {
        return aName;
    }

    public void setaName(String aName) {
        assert aName != null;
        this.aName = aName;
    }
    @Override
    public boolean equals(Object obj){
        if(obj == null || obj.getClass() != this.getClass()) return false;
        if(obj == this) return true;

        VIP other = (VIP) obj;
        return other.getaName().equals(this.getaName());
    }
    @Override
    public int hashCode(){
        return aName.hashCode();
    }
}
