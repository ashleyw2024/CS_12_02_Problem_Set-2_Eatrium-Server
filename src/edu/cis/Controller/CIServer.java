/*
 * File: CIServer.java
 * ------------------------------
 * When it is finished, this program will implement a basic
 * ecommerce network management server.  Remember to update this comment!
 */

package edu.cis.Controller;

import acm.program.*;
import edu.cis.Model.*;
import edu.cis.Utils.SimpleServer;

import java.util.ArrayList;

public class CIServer extends ConsoleProgram
        implements SimpleServerListener
{

    /* The internet port to listen to requests on */
    private static final int PORT = 8000;

    /* The server object. All you need to do is start it */
    private SimpleServer server = new SimpleServer(this, PORT);
    private ArrayList<CISUser> users = new ArrayList<>();
    private ArrayList<MenuItem> menu = new ArrayList<>();
    private ArrayList<MenuItem> order = new ArrayList<>();

    /**
     * Starts the server running so that when a program sends
     * a request to this server, the method requestMade is
     * called.
     */
    public void run()
    {
        println("Starting server on port " + PORT);
        server.start();
    }

    /**
     * When a request is sent to this server, this method is
     * called. It must return a String.
     *
     * @param request a Request object built by SimpleServer from an
     *                incoming network request by the client
     */
    public String requestMade(Request request)
    {
        String cmd = request.getCommand();
        println(request.toString());

        if(request.getCommand().equals(CISConstants.CREATE_USER)){
            return createUser(request);
        }
        if(request.getCommand().equals(CISConstants.ADD_MENU_ITEM)){
            return addMenuItem(request);
        }
        if(request.getCommand().equals(CISConstants.PLACE_ORDER)){
            return placeOrder(request);
        }
        if(request.getCommand().equals(CISConstants.DELETE_ORDER)){
            return deleteOrder(request);
        }
        if(request.getCommand().equals(CISConstants.GET_ORDER)){
            return getOrder(request);
        }
        if(request.getCommand().equals(CISConstants.GET_ITEM)){
            return getItem(request);
        }
        if(request.getCommand().equals(CISConstants.GET_USER)){
            return getUser(request);
        }
        if(request.getCommand().equals(CISConstants.GET_CART)){
            return getCart(request);
        }
        if (request.getCommand().equals(CISConstants.PING))
        {
            final String PING_MSG = "Hello, internet";

            //println is used instead of System.out.println to print to the server GUI
            println("   => " + PING_MSG);
            return PING_MSG;
        }
        return "Error: Unknown command " + cmd + ".";
    }

    public static void main(String[] args)
    {
        CIServer f = new CIServer();
        f.start(args);
    }

    public String createUser(Request req)
    {
        String userID = req.getParam(CISConstants.USER_ID_PARAM);

        String userName = req.getParam(CISConstants.USER_NAME_PARAM);

        String yearLevel = req.getParam(CISConstants.YEAR_LEVEL_PARAM);

        if(userID == null||userName == null||yearLevel == null)
        {
            return CISConstants.PARAM_MISSING_ERR;
        }

        CISUser user = new CISUser(userID, userName, yearLevel);

        users.add(user);

        return CISConstants.SUCCESS;
    }

    public String addMenuItem(Request req)
    {
        String itemName = req.getParam(CISConstants.ITEM_NAME_PARAM);

        String description = req.getParam(CISConstants.DESC_PARAM);

        double price = Double.parseDouble(req.getParam(CISConstants.PRICE_PARAM));

        String itemType = req.getParam(CISConstants.ITEM_TYPE_PARAM);

        String itemID = req.getParam(CISConstants.ITEM_ID_PARAM);

        if(itemID==null||itemName==null||itemType==null||description==null||req.getParam(CISConstants.PRICE_PARAM)==null){
            return CISConstants.PARAM_MISSING_ERR;
        }
        MenuItem newItem = new MenuItem(itemName, description, price, itemID, itemType);

        menu.add(newItem);

        return CISConstants.SUCCESS;
    }

    public String placeOrder(Request req){
        String orderID = req.getParam(CISConstants.ORDER_ID_PARAM);

        String menuItemID = req.getParam(CISConstants.ITEM_ID_PARAM);

        String userID = req.getParam(CISConstants.USER_ID_PARAM);

        String orderType = req.getParam(CISConstants.ORDER_TYPE_PARAM);

        //check if the user already exists
        if(orderID==null||menuItemID==null||userID==null||orderType==null){
            return CISConstants.PARAM_MISSING_ERR;
        }
        if(menu.isEmpty()){
            return CISConstants.EMPTY_MENU_ERR;
        }
        //check if user exists
        boolean userExists = false,hasOrderID = false,hasDupOrderID=false,soldOut=false,hasItem=false;
        CISUser testingUser = null;
        //MenuItem targetItem = new MenuItem();
        for(CISUser user:users){
            if(user.getUserID().equals(userID)){
                userExists = true;
                testingUser = user;
                break;
            }
        }
        if(!userExists){
            return CISConstants.USER_INVALID_ERR;
        }

        //checks if user has order
        for(Order order: testingUser.getOrders()){
            if(order.getOrderID().equals(orderID)){
                hasOrderID=true;
                break;
            }
        }
        if(hasOrderID){
            return CISConstants.DUP_ORDER_ERR;
        }
        for(CISUser user:users){
            for(Order order:user.getOrders()){
                if(order.getOrderID().equals(orderID)){
                    hasDupOrderID=true;
                    break;
                }
            }
        }
        if(hasDupOrderID){
            return CISConstants.DUP_USER_ERR;
        }
        MenuItem targetItem = null;
        for(MenuItem item : menu){
            if(item.getId().equals(menuItemID)){
                hasItem = true;
                targetItem = item;
                if(item.getAmountAvailable()==0){
                    soldOut = true;
                }
                break;
            }
        }
        if(soldOut){
            return CISConstants.SOLD_OUT_ERR;
        }
        if(!hasItem){
            return CISConstants.INVALID_MENU_ITEM_ERR;
        }
        if(testingUser.getMoney() < targetItem.getPrice()){
            return CISConstants.USER_BROKE_ERR;
        }
        Order order = new Order(menuItemID, orderType, orderID);
        testingUser.getOrders().add(order);
        testingUser.setMoney(testingUser.getMoney()-targetItem.getPrice());
        targetItem.setAmountAvailable(targetItem.getAmountAvailable()-1);
        return CISConstants.SUCCESS;
    }

    public String deleteOrder(Request req){
        String userID = req.getParam(CISConstants.USER_ID_PARAM);
        String orderID = req.getParam(CISConstants.ORDER_ID_PARAM);

        if(userID == null || orderID == null){
            return CISConstants.PARAM_MISSING_ERR;
        }

        for(CISUser user: users){
            if(user.getUserID().equals(userID)){
                for(Order order:user.getOrders()){
                    if(order.getOrderID().equals(orderID)){
                        user.getOrders().remove(order);
                        return CISConstants.SUCCESS;
                    }
                }
            }
        }
        return CISConstants.ORDER_INVALID_ERR;
    }

    public String getOrder(Request req){
        String userID = req.getParam(CISConstants.USER_ID_PARAM);
        String orderID = req.getParam(CISConstants.ORDER_ID_PARAM);
        if(userID==null||orderID==null){
            return CISConstants.PARAM_MISSING_ERR;
        }
        for(CISUser user:users){
            if(user.getUserID().equals(userID)){
                for(Order order:user.getOrders()){
                    if(order.getOrderID().equals(orderID)){
                        return "Order{itemID='"+order.getItemID()+"', type='"+order.getType()+"', orderID='"+order.getOrderID()+"'}";
                    }
                }
            }
        }
        return CISConstants.ORDER_INVALID_ERR;
    }

    public String getUser(Request req){
        String userID = req.getParam(CISConstants.USER_ID_PARAM);
        for(CISUser user:users){
            if(user.getUserID().equals(userID)){
                return "CISUser{userID='"+user.getUserID()+"', name='"+user.getName()+"', yearLevel='"+user.getYearLevel()+"', "+getCart(req)+"money="+String.valueOf(user.getMoney())+"}";
            }
        }
        return CISConstants.USER_INVALID_ERR;
    }

    public String getItem(Request req){
        String itemID = req.getParam(CISConstants.ITEM_ID_PARAM);
        for(MenuItem item: menu){
            if(item.getId().equals(itemID)){
                return "MenuItem{name='"+ item.getName()+ "', description='" + item.getDescription()+ "', price="+String.valueOf(item.getPrice())+", id='" +item.getId()+ "', amountAvailable="+String.valueOf(item.getAmountAvailable())+", type='"+item.getType()+"'}";
            }
        }
        return CISConstants.INVALID_MENU_ITEM_ERR;
    }

    public String getCart(Request req){
        String userID = req.getParam(CISConstants.USER_ID_PARAM);
        for(CISUser user:users){
            if(user.getUserID().equals(userID)){
                String cart ="orders= ";
                for(Order order: user.getOrders()){
                    Request request = new Request(CISConstants.GET_ORDER);
                    request.addParam(CISConstants.USER_ID_PARAM, userID);
                    request.addParam(CISConstants.ORDER_ID_PARAM, order.getOrderID());
                    cart += getOrder(request)+", ";
                }
                return cart;
            }
        }
        return CISConstants.USER_INVALID_ERR;
    }
}