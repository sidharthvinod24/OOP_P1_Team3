package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;
import com.myg2x.game.lwjgl3.AbstractEngine;

public class FinalEquationScreen extends EquationScreen{

	private int initiallength;
	private Inventory inventory;
	private List<MathOperatorObject> usedItems = new ArrayList<>();
	
	public FinalEquationScreen(AbstractEngine game, String value) {
		super(game, value);
		
		this.inventory = game.getInventory();
		
		// TODO Auto-generated constructor stub	
		
		equation = RandomiseEqn();
        question = (String) equation.get(0);
        answer = Integer.toString((int) equation.get(1));
        reply = Integer.toString((int) equation.get(2));
        initiallength = reply.length();
	}


	public void render(float delta) {
		
        logic(delta);
        draw();
        
	}
	
	public void draw()
	{
		game.DrawGridScreen();
        game.viewport.apply();
        game.batch.begin();
        	// Draw overlay and equation texts
	        game.batch.draw(overlay, 175, 125, 450, 250);
	        font.draw(game.batch, question, 225, 325);
	        font.draw(game.batch, "Current: ", 212.5f, 237.5f);
	        font.draw(game.batch, reply, 410, 237.5f);
        game.batch.end();
	}

	public void logic(float delta) {
        if (Gdx.input.isKeyPressed(Keys.Q)) {
            game.SetGridScreen();
        }

        // Process answer input using an InputAdapter
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                int number = -1;
                String lastKey = null;
                
                // Numbers and Operators input check
                if (keycode >= Keys.NUM_0 && keycode <= Keys.NUM_9) {
                    number = keycode - Keys.NUM_0;
                    lastKey = String.valueOf(number);   
                    
                    // Check if number is in inventory 
                    if (checkNumberInInventory(lastKey) && reply.length() < initiallength + 2) {
                        reply += lastKey;
                        removeItemFromInventory(lastKey);
                    }
                } else if (keycode >= Keys.NUMPAD_0 && keycode <= Keys.NUMPAD_9) {
                    number = keycode - Keys.NUMPAD_0;
                    lastKey = String.valueOf(number);  
                    
                    // Check if number is in inventory 
                    if (checkNumberInInventory(lastKey) && reply.length() < initiallength + 2) {
                        reply += lastKey;
                        removeItemFromInventory(lastKey);
                    }
                } else if (keycode == Keys.BACKSPACE) {
                    if (!reply.isEmpty() && reply.length() > initiallength) {
                    	// Get the character to return to inventory
                    	String lastKeyToReturn = reply.substring(reply.length() - 1);
                    	// Remove the character from the reply string
                        reply = reply.substring(0, reply.length() - 1);
                        returnItemToInventory(lastKeyToReturn);
                    }
                
                
                } else if (keycode == Keys.EQUALS || keycode == Keys.NUMPAD_ADD) {
                	lastKey = "+";
                    if (checkOperatorInInventory(lastKey) && reply.length() < initiallength + 2) {
                        reply += lastKey;
                        removeItemFromInventory(lastKey);
                    }
                    
                } else if (keycode == Keys.MINUS || keycode == Keys.NUMPAD_SUBTRACT) {
                	lastKey = "-";
                    if (checkOperatorInInventory(lastKey) && reply.length() < initiallength + 2) {
                        reply += lastKey;
                        removeItemFromInventory(lastKey);
                    }
                    
                } else if (keycode == Keys.X || keycode == Keys.NUMPAD_MULTIPLY) {
                	lastKey = "*";
                    if (checkOperatorInInventory(lastKey) && reply.length() < initiallength + 2) {
                        reply += lastKey;
                        removeItemFromInventory(lastKey);
                    }
                    
                } else if (keycode == Keys.SLASH || keycode == Keys.NUMPAD_DIVIDE) {
                	lastKey = "/";
                    if (checkOperatorInInventory(lastKey) && reply.length() < initiallength + 2) {
                        reply += lastKey;
                        removeItemFromInventory(lastKey);
                    }


                } else if (keycode == Keys.ENTER) {
                    if (!reply.isEmpty()) {

                    	List<Object> processed = processEqn(reply);
                    	reply = (String)processed.get(0);
                    	boolean check = (boolean) processed.get(1);
                    	
                    	
                        if(isNumeric(reply) && check == true) {
                        	initiallength = reply.length();
	                    	if (Integer.parseInt(reply) == Integer.parseInt(answer)) {
	                            System.out.println("CORRECT!!!");
	                            //Increment level
	                            game.getGridScreen().setLevel(1);
	                            
	                            //If not level 5
	                            if(game.getGridScreen().getLevel() < 6) 
	                            {
	                            	// Clear all used items from inventory
		                            usedItems.clear();
		                            game.ResetTimer();
		                            game.SetGridScreen();
	                            }
	                            else //If level 5 final equation reached, WI!!
	                            {
	                            	
	                            	game.GetGameOverScreen().setState(true);
	                            	game.SetGameOverScreen();
	                            }
	                            

	                        } else {

	                            System.out.println("CONTINUE, ANS NOT FOUND");
	                        }
                        }
                        else {
                        	returnAllItemsToInventory();
                        	reply = reply.substring(0,initiallength);
                        	System.out.println("CONTINUE, PROCESS ERROR");
                        }
                    }
                }
                return false; // Allow further input processing
            }
        });
    }
	
	// Check if number is in inventory
	private boolean checkNumberInInventory(String number) {
		for (MathOperatorObject item: inventory.getItems()) {
			if (item.getValue().equals(number) && item.getCount() > 0) {
				return true;
			}
		}
		return false;
	}
	
	// Check if operator is in inventory
	private boolean checkOperatorInInventory(String operator) {
		for (MathOperatorObject item : inventory.getItems()) {
            if (item.getValue().equals(operator) && item.getCount() > 0) {
                return true;
            }
        }
        return false;
	}
	
	// Remove item from inventory
    private void removeItemFromInventory(String value) {
    	for (MathOperatorObject item : inventory.getItems()) {
            if (item.getValue().equals(value) && item.getCount() > 0) {
            	item.decrementCount();
                usedItems.add(item);
                
                break;
            }
        }
    }

    // Return single item to inventory
    private void returnItemToInventory(String value) {
        Iterator<MathOperatorObject> iterator = usedItems.iterator();
        while (iterator.hasNext()) {
            MathOperatorObject item = iterator.next();	
            if (item.getValue().equals(value)) {
                // If item is not in inventory, add it back
                boolean itemExists = false;
                for (MathOperatorObject invItem : inventory.getItems()) {
                    if (invItem.getValue().equals(value)) {
                        invItem.incrementCount();
                        itemExists = true;
                        break;
                    }
                }
                
                // If item doesn't exist in inventory, add it back
                if (!itemExists) {
                    inventory.getItems().add(item);
                    item.incrementCount();
                }
                
                iterator.remove();
                break;
            }
        }
    }
    
    // Return all items to inventory
    private void returnAllItemsToInventory() {
    	 for (MathOperatorObject item : usedItems) {
             // Check if item exists in inventory
             boolean itemExists = false;
             for (MathOperatorObject invItem : inventory.getItems()) {
                 if (invItem.getValue().equals(item.getValue())) {
                     invItem.incrementCount();
                     itemExists = true;
                     break;
                 }
             }
             
             // If item doesn't exist in inventory, add it back
             if (!itemExists) {
                 inventory.getItems().add(item);
                 item.incrementCount();
             }
         }
         
         // Clear used items
         usedItems.clear();
    }
    
	private List<Object> processEqn(String eqn) {
		String processed = "";
		int operatorIndex = -1;
	    char operator = ' ';

		for (int i = 1; i < eqn.length(); i++) {
	        char c = eqn.charAt(i);
	        
	        if (c == '+' || c == '-' || c == '*' || c == '/') {
	            operator = c;
	            operatorIndex = i;
	            break;
	        }
	    }

		if (operatorIndex == -1) {
			System.out.println("NO OPERATOR FOUND");
			return Arrays.asList(eqn, false);
		}

		String leftStr = eqn.substring(0, operatorIndex);
	    String rightStr = eqn.substring(operatorIndex + 1);
	    int left;
	    int right;

	    try {
	        left = Integer.parseInt(leftStr);
	        right = Integer.parseInt(rightStr);
	    }
	    catch(NumberFormatException e) {
	    	return Arrays.asList(eqn, false);
	    }

            // Perform the calculation based on the operator
        switch (operator) {
            case '+':
                return Arrays.asList(Integer.toString(left + right), true);
            case '-':
                return Arrays.asList(Integer.toString(left - right), true);
            case '*':
                return Arrays.asList(Integer.toString(left * right), true);
            case '/':
                if (right == 0) {
                    throw new IllegalArgumentException("Division by zero is not allowed");
                }
                return Arrays.asList(Integer.toString(left / right), true);
            default:
                throw new IllegalArgumentException("Unsupported operator: " + operator);
        }

	}

	private boolean isNumeric(String value) {
		try {
			int i = Integer.parseInt(value);
		}
		catch (NumberFormatException e) {
			return false;
		}
		return true;

	}

	public List<Object> RandomiseEqn() {

		ArrayList<MathOperatorObject> itemcount = inventory.getItems();
		int[] usedtracker = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		int randomoperator;
		int randomdigit;
		
		int num1 = rand.nextInt(20 - 1) + 1;
		int ans = num1;
		
		
		for (int i = 0; i < rand.nextInt(2,4); i++) {

			randomoperator = rand.nextInt(9, 13);
			randomdigit = rand.nextInt(0, 9);
			while(usedtracker[randomdigit] > itemcount.get(randomdigit).getCount()) {
				if (randomdigit == 8) {
					randomdigit = 0;
				}
				else {
					randomdigit++;
				}
			}
			
			while(true) {
				switch (randomoperator){
				
					case 9: // +
						if(usedtracker[randomoperator] >= itemcount.get(randomoperator).getCount()) {
							randomoperator++;
							continue;
						}
						
						else {
							usedtracker[randomoperator]++;
							usedtracker[randomdigit]++;
							ans += Integer.parseInt(itemcount.get(randomdigit).getValue());
							
							break;
						}
						
					case 10: // -
						if(usedtracker[randomoperator] >= itemcount.get(randomoperator).getCount()) {
							randomoperator++;
							continue;
						}
						
						else {
							usedtracker[randomoperator]++;
							usedtracker[randomdigit]++;
							ans -= Integer.parseInt(itemcount.get(randomdigit).getValue());
							
							break;
						}
					case 11: // *
						if(usedtracker[randomoperator] >= itemcount.get(randomoperator).getCount()) {
							randomoperator++;
							continue;
						}
						
						else {
							usedtracker[randomoperator]++;
							usedtracker[randomdigit]++;
							ans *= Integer.parseInt(itemcount.get(randomdigit).getValue());
							
							break;
						}
					case 12: // /
						if(usedtracker[randomoperator] >= itemcount.get(randomoperator).getCount()) {
							randomoperator = 9;
							continue;
						}
						
						else {
							usedtracker[randomoperator]++;
							usedtracker[randomdigit]++;
							ans /= Integer.parseInt(itemcount.get(randomdigit).getValue());
							
							break;
						}
						
				}
				break;
			}
		}
		
		//Check if initial given number is equal to target number
		if(ans == num1) { // this is super unlikely but it happened during testing somehow
			ans += rand.nextInt(1, 40);
		}
		
		String eqn = "Make " + ans;


		return Arrays.asList(eqn, ans, num1);
	}
	
	public void NewEqn() {
		equation = RandomiseEqn();
        question = (String) equation.get(0);
        answer = Integer.toString((int) equation.get(1));
        reply = Integer.toString((int) equation.get(2));
        initiallength = reply.length();
	}
	public boolean GameOverCheck() {
		
		int opcount = 0;
		int numcount = 0;
		//Count number of operators
		for(int i = 9; i < 13; i++) {
			opcount += inventory.getItems().get(i).getCount();
		}
		
		//Count number of numbers
		for(int i = 0; i < 9; i++) {
			numcount += inventory.getItems().get(i).getCount();
		}
		
		//If too little operators or numbers, game over
		if(opcount < 4 || numcount < 4) {
			System.out.println("YOU LOSE!!!");

			return false;
		}
		else {
			return true;
		}
		
	}
}
