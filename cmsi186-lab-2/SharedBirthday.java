public class SharedBirthday {

    public static void main(String[] args) {
        try {
            if (args.length != 3) {
                throw new IllegalArgumentException("Exactly three arguments required");
            }
            int people = Integer.parseInt(args[0]);
            int days = Integer.parseInt(args[1]);
            int trials = Integer.parseInt(args[2]);
            System.out.println(probabilityEstimate(people, days, trials));
        } catch (NumberFormatException e) {
            System.err.println("Arguments must all be integers");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    public static double probabilityEstimate(int people, int days, int trials) {
        if (people < 2) {
          throw new IllegalArgumentException("At least two people required");
        }
        if (days < 1) {
          throw new IllegalArgumentException("At least one day required");
        }
        if (trials < 1) {
          throw new IllegalArgumentException("At least one trial required");
        }

        int total = 0;
		    boolean sameBirthdayExists = false;
		    int[] listOfBirthdays = new int[people];
        /*
        * This loop will iterate 'trials' times producing 'people' amount of birthdays
        * ranging from 0 to 'days'. These dates are stored and checked for a same birthday.
        */
    		for(int i = 0; i < trials; i++) {
    		   sameBirthdayExists = false;
           //Produces 'people' amount of birthdays randomly and stores them in an array
    		   for(int j = 0; j < people; j++) {
    			     listOfBirthdays[j] = (int) ((Math.random() * days) + 1);
    			 }
    			 for (int k = 0; k < listOfBirthdays.length; k++) {
    	        for (int l = 0; l < listOfBirthdays.length; l++) {
                /*
                * Checks the 'listOfBirthdays' array to see if same birthday exists,
                * if a same birthday does exist, the nested loop breaks
                */
    	           if (listOfBirthdays[k] == (listOfBirthdays[l]) && k != l) {
    	              total++;
    	              sameBirthdayExists = true;
    	              break;
    	           }
    	         }
    	         if(sameBirthdayExists) {
    	          break;
    	        }
    	      }
      	}
      	return ((double)total / (double)trials);
    }

    //
    // TODO: Don't be afraid to write private helper methods to keep your code modular.
    //
}
