import java.util.Comparator;
public class CompareSlot implements Comparator<Slot> {
    public int compare(Slot slotOne, Slot slotTwo) {
        if (slotOne.getMinimumRequired() == 0 && slotTwo.getMinimumRequired() > 0) {
            return 1; // Slot one is not important at all compared to this slot.
        }
        if (slotTwo.getMinimumRequired() == 0 && slotOne.getMinimumRequired() > 0) {
            return -1; // Slot two is not important at all compared to this slot.
        }
        if (slotOne.getMinimumRequired() == 0 && slotTwo.getMinimumRequired() == 0) {
            if (Math.random() < 0.5) // We do not care the order we fill these slots because they both will not be filled.
                return -1;
            else {
                return 1;
            }
        }
        if ((slotOne.isFilledToMin()) && (!slotTwo.isFilledToMin())) {
            return 1; // slotOne is less important than slot two so it will be farther in a sorted list.
        }
        if ((!slotOne.isFilledToMin() && slotTwo.isFilledToMin())) {
            return -1; // slotOne is more important to fill then slotTwo.
        }
        // If both slots are not filled ot the minimum required we will use this to compare the slots
        if (!slotOne.isFilledToMin() && !slotTwo.isFilledToMin()){
            double slotOneRatio = slotOne.getNumberCurrentlyAvailable() / (slotOne.getMinimumRequired() - slotOne.getNumberOfPeopleWorking() + 0.0 );
            double slotTwoRatio = slotTwo.getNumberCurrentlyAvailable() / ( slotTwo.getMinimumRequired() - slotTwo.getNumberOfPeopleWorking()+ 0.0 );
            if (slotOneRatio == slotTwoRatio){
                if (Math.random() < 0.5){
                    return 1;
                }
                else {
                    return -1;
                }
            }
            if (slotOneRatio > slotTwoRatio){
                return 1;
            }
            else {
                return -1;
            }
        }

        else {
            if (slotOne.atMax() && slotTwo.atMax()){
                if (Math.random() < 0.5){
                    return 1;
                }
                else{
                    return -1;
                }
            }
            if (slotOne.atMax() && !slotTwo.atMax()){
                return 1;
            }
            if (slotTwo.atMax() && !slotOne.atMax()){
                return -1;
            }
            double slotOneRatio = slotOne.getNumberCurrentlyAvailable() / (slotOne.getLeftToMax() + 0.0);
            double slotTwoRatio = slotTwo.getNumberCurrentlyAvailable() / (slotTwo.getLeftToMax() + 0.0);
            if (slotOneRatio == slotTwoRatio){
                if (Math.random() < 0.5){
                    return 1;
                }
                else {
                    return -1;
                }
            }
            if (slotOneRatio > slotTwoRatio){
                return 1;
            }
            else {
                return -1;
            }
        }
    }
}
