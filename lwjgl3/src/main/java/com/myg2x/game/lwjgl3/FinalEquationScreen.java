package com.myg2x.game.lwjgl3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;

public class FinalEquationScreen extends EquationScreen{

	private int initiallength;
	private Inventory inventory;
	private List<MathOperatorObject> usedItems = new ArrayList<>();
	
	public FinalEquationScreen(AbstractEngine game, String value) {
		super(game, value);
		
		this.inventory = game.getInventory();
		
		// TODO Auto-generated constructor stub	
		
		equation = RandomiseEqn(value);
        question = (String) equation.get(0);
        answer = Integer.toString((int) equation.get(1));
        reply = Integer.toString((int) equation.get(2));
        initiallength = reply.length();
	}


	public void render(float delta) {
		game.DrawGridScreen();
        logic(delta);

        game.viewport.apply();
        batch.begin();
        // Draw overlay and equation texts
        batch.draw(overlay, 175, 125, 450, 250);
        font.draw(batch, question, 225, 325);
        font.draw(batch, "Current: ", 212.5f, 237.5f);
        font.draw(batch, reply, 410, 237.5f);
        batch.end();
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
                
                // Numbers input check
                if (keycode >= Keys.NUM_0 && keycode <= Keys.NUM_9) {
                    number = keycode - Keys.NUM_0;
                    lastKey = String.valueOf(number);   
                    
                    // Check if number is in inventory 
                    if (checkNumberInInventory(lastKey) && reply.length() < 7) {
                        reply += lastKey;
                        removeItemFromInventory(lastKey);
                    }
                } else if (keycode >= Keys.NUMPAD_0 && keycode <= Keys.NUMPAD_9) {
                    number = keycode - Keys.NUMPAD_0;
                    lastKey = String.valueOf(number);  
                    
                    // Check if number is in inventory 
                    if (checkNumberInInventory(lastKey) && reply.length() < 7) {
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
                    if (checkOperatorInInventory(lastKey) && reply.length() < 7) {
                        reply += lastKey;
                        removeItemFromInventory(lastKey);
                    }
                    
                } else if (keycode == Keys.MINUS || keycode == Keys.NUMPAD_SUBTRACT) {
                	lastKey = "-";
                    if (checkOperatorInInventory(lastKey) && reply.length() < 7) {
                        reply += lastKey;
                        removeItemFromInventory(lastKey);
                    }
                    
                } else if (keycode == Keys.X || keycode == Keys.NUMPAD_MULTIPLY) {
                	lastKey = "*";
                    if (checkOperatorInInventory(lastKey) && reply.length() < 7) {
                        reply += lastKey;
                        removeItemFromInventory(lastKey);
                    }
                    
                } else if (keycode == Keys.SLASH || keycode == Keys.NUMPAD_DIVIDE) {
                	lastKey = "/";
                    if (checkOperatorInInventory(lastKey) && reply.length() < 7) {
                        reply += lastKey;
                        removeItemFromInventory(lastKey);
                    }


                } else if (keycode == Keys.ENTER) {
                    if (!reply.isEmpty()) {

                    	reply = processEqn(reply);

                        if(isNumeric(reply)) {
	                    	if (Integer.parseInt(reply) == Integer.parseInt(answer)) {
	                            System.out.println("CORRECT!!!");
	                            
	                            // Clear all used items from inventory
	                            usedItems.clear();
	                            
	                            equation = RandomiseEqn("1");
	                            question = (String) equation.get(0);
	                            answer = Integer.toString((int) equation.get(1));
	                            reply = Integer.toString((int) equation.get(2));
	                            initiallength = reply.length();
	                            game.SetGridScreen();

	                        } else {
	                        	returnAllItemsToInventory();
	                        	reply = reply.substring(0,1);
	                            System.out.println("CONTINUE");
	                        }
                        }
                        else {
                        	returnAllItemsToInventory();
                        	reply = reply.substring(0,1);
                        	System.out.println("CONTINUE");
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
			if (item.getValue().equals(number)) {
				return true;
			}
		}
		return false;
	}
	
	// Check if operator is in inventory
	private boolean checkOperatorInInventory(String operator) {
		for (MathOperatorObject item : inventory.getItems()) {
            if (item.getValue().equals(operator)) {
                return true;
            }
        }
        return false;
	}
	
	// Remove item from inventory
    private void removeItemFromInventory(String value) {
        Iterator<MathOperatorObject> iterator = inventory.getItems().iterator();
        while (iterator.hasNext()) {
            MathOperatorObject item = iterator.next();
            if (item.getValue().equals(value)) {
                usedItems.add(item);
                iterator.remove();
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
                inventory.getItems().add(item);
                iterator.remove();
                break;
            }
        }
    }
    
    // Return all items to inventory
    private void returnAllItemsToInventory() {
        inventory.getItems().addAll(usedItems);
        usedItems.clear();
    }
    
	private String processEqn(String eqn) {
		String processed = "";
		int operatorIndex = -1;
	    char operator = ' ';

		for (int i = 0; i < eqn.length(); i++) {
	        char c = eqn.charAt(i);
	        if (c == '+' || c == '-' || c == '*' || c == '/') {
	            operator = c;
	            operatorIndex = i;
	            break;
	        }
	    }

		if (operatorIndex == -1) {
			System.out.println("NO OPERATOR FOUND");
			return eqn;
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
	    	return eqn;
	    }

            // Perform the calculation based on the operator
        switch (operator) {
            case '+':
                return Integer.toString((int) left + right);
            case '-':
                return Integer.toString((int) left - right);
            case '*':
                return Integer.toString((int)left * right);
            case '/':
                if (right == 0) {
                    throw new IllegalArgumentException("Division by zero is not allowed");
                }
                return Integer.toString((int)left / right);
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

	public List<Object> RandomiseEqn(String value) {


		int num1 = rand.nextInt(20 - 1) + 1;
		int ans = rand.nextInt(80 - 1) + 1 + num1;

		String eqn = "Make " + ans;


		return Arrays.asList(eqn, ans, num1);
	}
}
