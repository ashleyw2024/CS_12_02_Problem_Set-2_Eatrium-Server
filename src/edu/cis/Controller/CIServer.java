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
    ArrayList<CISUser> users = new ArrayList<CISUser>();
    Menu menu = new Menu();
    /**
     * Starts the server running so that when a program sends
     * a request to this server, the method requestMade is
     * called.
     */
    public void run()
    {
        println("Starting server on port " + PORT);
        server.start();
        users = new ArrayList<>();
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
            String createUser = createUser(request);
            return createUser;
        }
        if(request.getCommand().equals(CISConstants.ADD_MENU_ITEM)){
            String addMenuItem = addMenuItem(request);
            return addMenuItem;
        }
        if(request.getCommand().equals(CISConstants.PLACE_ORDER)){
            String placeOrder = placeOrder(request);
            return placeOrder;
        }
        if(request.getCommand().equals(CISConstants.DELETE_ORDER)){
            String deleteOrder = deleteOrder(request);
            return deleteOrder;
        }
        if(request.getCommand().equals(CISConstants.GET_ORDER)){
            String getOrder = getOrder(request);
            return getOrder;
        }
        if(request.getCommand().equals(CISConstants.GET_ITEM)){
            String getItem = getItem(request);
            return getItem(request);
        }
        if(request.getCommand().equals(CISConstants.GET_USER)){
            String getUser = getUser(request);
            return getUser;
        }
        if(request.getCommand().equals(CISConstants.GET_CART)){
            String getCart = getCart(request);
            return getCart;
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
    public String createUser(Request req) {
        String userID = req.getParam(CISConstants.USER_ID_PARAM), name = req.getParam(CISConstants.USER_NAME_PARAM), yearLevel = req.getParam(CISConstants.YEAR_LEVEL_PARAM);
        if(userID == null||name == null||yearLevel == null) {
            return CISConstants.PARAM_MISSING_ERR;
        }
        CISUser user = new CISUser(userID,name,yearLevel);
        users.add(user);
        return CISConstants.SUCCESS;
    }
    public String addMenuItem(Request req){
        String userID = req.getParam(CISConstants.ITEM_ID_PARAM),name = req.getParam(CISConstants.ITEM_NAME_PARAM),description=req.getParam(CISConstants.DESC_PARAM),type=req.getParam(CISConstants.ITEM_TYPE_PARAM);
        double price = Double.valueOf(req.getParam(CISConstants.PRICE_PARAM));
        if(userID==null||name==null||type==null||description==null||req.getParam(CISConstants.PRICE_PARAM)==null){
            return CISConstants.PARAM_MISSING_ERR;
        }
        MenuItem newItem = new MenuItem(name,description,price,userID,1,type);
        menu.addEatriumItems(newItem);
        return CISConstants.SUCCESS;
    }

    public String placeOrder(Request req){
        String orderId = req.getParam(CISConstants.ORDER_ID_PARAM),
                itemId = req.getParam(CISConstants.ITEM_ID_PARAM),
                userId = req.getParam(CISConstants.USER_ID_PARAM),
                orderType = req.getParam(CISConstants.ORDER_TYPE_PARAM);
        if(orderId==null||itemId==null||userId==null||orderType==null){
            return CISConstants.PARAM_MISSING_ERR;
        }
        if(menu.getEatriumItems().isEmpty()){
            return CISConstants.EMPTY_MENU_ERR;
        }
        boolean userExists = false,hasOrderID = false,hasDupOrderID=false,soldOut=false,hasItem=false;
        CISUser testingUser = new CISUser();
        MenuItem targetItem = new MenuItem();
        for(CISUser user:users){
            if(user.getUserID().equals(userId)){
                userExists = true;
                testingUser = user;
                break;
            }
        }
        if(!userExists){
            return CISConstants.USER_INVALID_ERR;
        }
        for(Order order: testingUser.getOrders()){
            if(order.getOrderID().equals(orderId)){
                hasOrderID=true;
                break;
            }
        }
        if(hasOrderID){
            return CISConstants.DUP_ORDER_ERR;
        }
        for(CISUser user:users){
            for(Order order:user.getOrders()){
                if(order.getOrderID().equals(orderId)){
                    hasDupOrderID=true;
                    break;
                }
            }
        }
        if(hasDupOrderID){
            return CISConstants.DUP_USER_ERR;
        }
        for(MenuItem item:menu.getEatriumItems()){
            if(item.getId().equals(itemId)){
                hasItem=true;
                targetItem=item;
                if(item.getAmountAvailable()==0){
                    soldOut=true;
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
        if(testingUser.getMoney()<targetItem.getPrice()){
            return CISConstants.USER_BROKE_ERR;
        }
        Order order = new Order(itemId, userId, orderType);
        testingUser.addOrder(order);
        testingUser.setMoney(testingUser.getMoney()-targetItem.getPrice());
        targetItem.setAmountAvailable(targetItem.getAmountAvailable()-1);
        return CISConstants.SUCCESS;
    }

    public String deleteOrder(Request req){
        String userId=req.getParam(CISConstants.USER_ID_PARAM),orderId=req.getParam(CISConstants.ORDER_ID_PARAM);
        if(userId==null||orderId==null){
            return CISConstants.PARAM_MISSING_ERR;
        }
        for(CISUser user: users){
            if(user.getUserID().equals(userId)){
                for(Order order:user.getOrders()){
                    if(order.getOrderID().equals(orderId)){
                        user.deleteOrder(order);
                        return CISConstants.SUCCESS;
                    }
                }
            }
        }
        return CISConstants.ORDER_INVALID_ERR;
    }

    public String getOrder(Request req){
        String userId = req.getParam(CISConstants.USER_ID_PARAM),orderId = req.getParam(CISConstants.ORDER_ID_PARAM);
        if(userId==null||orderId==null){
            return CISConstants.PARAM_MISSING_ERR;
        }
        for(CISUser user:users){
            if(user.getUserID().equals(userId)){
                for(Order order:user.getOrders()){
                    if(order.getOrderID().equals(orderId)){
                        return "Order{itemID='"+order.getItemID()+"', type='"+order.getType()+"', orderID='"+order.getOrderID()+"'}";
                    }
                }
            }
        }
        return CISConstants.ORDER_INVALID_ERR;
    }

    public String getUser(Request req){
        String userID=req.getParam(CISConstants.USER_ID_PARAM);
        for(CISUser user:users){
            if(user.getUserID().equals(userID)){
                return "CISUser{userID='"+user.getUserID()+"', name='"+user.getName()+"', yearLevel='"+user.getYearLevel()+"', "+getCart(req)+"money="+String.valueOf(user.getMoney())+"}";
            }
        }
        return CISConstants.USER_INVALID_ERR;
    }

    public String getItem(Request req){
        String itemId = req.getParam(CISConstants.ITEM_ID_PARAM);
        for(MenuItem item: menu.getEatriumItems()){
            if(item.getId().equals(itemId)){
                return "MenuItem{name='"+ item.getName()+ "', description='" + item.getDescription()+ "', price="+String.valueOf(item.getPrice())+", id='" +item.getId()+ "', amountAvailable="+String.valueOf(item.getAmountAvailable())+", type='"+item.getType()+"'}";
            }
        }
        return CISConstants.INVALID_MENU_ITEM_ERR;
    }

    public String getCart(Request req){
        String userId = req.getParam(CISConstants.USER_ID_PARAM);
        for(CISUser user:users){
            if(user.getUserID().equals(userId)){
                String cart ="orders= ";
                for(Order order: user.getOrders()){
                    Request request = new Request(CISConstants.GET_ORDER);
                    request.addParam(CISConstants.USER_ID_PARAM, userId);
                    request.addParam(CISConstants.ORDER_ID_PARAM, order.getOrderID());
                    cart += getOrder(request)+", ";
                }
                return cart;
            }
        }
        return CISConstants.USER_INVALID_ERR;
    }
    public static void main(String[] args)
    {
        CIServer f = new CIServer();
        f.start(args);
        f.run();
    }
}