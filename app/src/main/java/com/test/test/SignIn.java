package com.test.test;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import io.swagger.client.ApiInvoker;
import io.swagger.client.Pair;
import io.swagger.client.api.PetApi;
import io.swagger.client.ApiException;
import io.swagger.client.api.StoreApi;
import io.swagger.client.api.UserApi;
import io.swagger.client.auth.Authentication;
import io.swagger.client.auth.HttpBasicAuth;
import io.swagger.client.model.Category;
import io.swagger.client.model.Pet;
import io.swagger.client.model.Tag;
import io.swagger.client.model.User;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/* COMP4521 Siu Chit 20270807 csiuab@connect.ust.hk
   COMP4521 Wong Pak Hing 20212714 phwongag@connect.ust.hk*/

public class SignIn extends AppCompatActivity implements View.OnClickListener{
    EditText editUserName, editPassword;
    private String userNameInput, passwordInput;
    private ProgressDialog signIn;
    final Context context = this;
    private FirebaseAuth firebaseAuth;
    String ext = "a@bar.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        editPassword = (EditText)findViewById(R.id.editPassword);
        editUserName = (EditText)findViewById(R.id.regEditUserName);
        signIn = new ProgressDialog(this);

        /* font */
        TextView signInWelcomText = (TextView)findViewById(R.id.signInWelcom);
        Typeface signInWelcomTextFont = Typeface.createFromAsset(getAssets(), "roboto_regular.ttf");
        signInWelcomText.setTypeface(signInWelcomTextFont);

        TextView signInUserNameText = (TextView)findViewById(R.id.signInUserName);
        Typeface signInUserNameTextFont = Typeface.createFromAsset(getAssets(), "roboto_regular.ttf");
        signInUserNameText.setTypeface(signInUserNameTextFont);

        TextView signInPasswordText = (TextView)findViewById(R.id.signInPassword);
        Typeface signInPasswordTextFont = Typeface.createFromAsset(getAssets(), "roboto_regular.ttf");
        signInPasswordText.setTypeface(signInPasswordTextFont);

        /* listeners */
        /*editUserName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View view, boolean hasFocus) {
                if(view.getId() == R.id.editUserName) {
                    if(editUserName.isFocused()){editUserName.setCursorVisible(true);}
                    else{editUserName.setCursorVisible(false);}
                }
            }
        });*/
        editUserName.setOnClickListener(this);

        Button signInBackwardButton = (Button)findViewById(R.id.signInBackwardButton);
        signInBackwardButton.setOnClickListener(this);

        Button signInForwardButton = (Button)findViewById(R.id.signInForwardButton);
        signInForwardButton.setOnClickListener(this);

        TextView signInRegLinkView = (TextView)findViewById(R.id.signInRegLink);
        signInRegLinkView.setOnClickListener(this);

        //initiate firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.signInBackwardButton) {
            Intent goBackLogInAndReg = new Intent(this, LogInAndReg.class);
            startActivity(goBackLogInAndReg);
        }
        else if(view.getId() == R.id.signInForwardButton){
            userNameInput = editUserName.getText().toString();
            passwordInput = editPassword.getText().toString();
            Log.i("The userName is ", userNameInput);
            Log.i("The password is ", passwordInput);
            loginUser(userNameInput,passwordInput);
        }
        else if(view.getId() == R.id.signInRegLink){
            Intent goToReg = new Intent(this, Registration.class);
            startActivity(goToReg);
        }
        else if(view.getId() == R.id.regEditUserName){
            editUserName.setCursorVisible(true);
        }
    }

    private void loginUser(String userNameInput,String passwordInput)  {
        String email = userNameInput.concat(ext);   //concat the Phone with a@foo.com

        //ApiInvoker api;
       /* UserApi user = new UserApi();

        try {
            user.loginUser("test", "abc123");
        }
        catch (Exception  e) {
            System.err.println("Error while logging in!");
            e.printStackTrace();
        }
*/
        PetApi apiInstance = new PetApi();
        Pet body = new Pet(); // Pet | Pet object that needs to be added to the store


        /*
        curl -X POST "https://petstore.swagger.io/v2/pet" -H "accept: application/json" -H "Content-Type: application/json" -d "{ \"id\": 0, \"category\": { \"id\": 0, \"name\": \"string\" }, \"name\": \"doggie\", \"photoUrls\": [ \"string\" ], \"tags\": [ { \"id\": 0, \"name\": \"string\" } ], \"status\": \"available\"}"
{"id":-9223372036854775808,"category":{"id":0,"name":"string"},"name":"doggie","photoUrls":["string"],"tags":[{"id":0,"name":"string"}],"status":"available"}
         */
        body.setId((long)123);
        Category cat = new Category();
        Tag tag = new Tag();
        //List<Tag>  tag = new List<Tag> Tag();
        tag.setId((long)0);
        tag.setName("TestTag");

        List<Tag> tags = new ArrayList<Tag>();
        tags.add(tag);




        List<String> strings = new ArrayList<String>();


        strings.add("PhotoUrls");
        /*
        List list = new ArrayList(); list.add("object 1")
         */

        cat.setId((long)0);
        cat.setName("Test");

        body.setCategory(cat);

        body.setName("Tester");


        body.setTags(tags);


        body.setPhotoUrls(strings);

        body.setStatus((Pet.StatusEnum.available));

        System.err.println("Test! " + body.toString());




        signIn.setMessage("Signing In ...");
        signIn.show();

        firebaseAuth.signInWithEmailAndPassword(email,passwordInput)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){    // if sign in success
                            finish();
                            Intent goToTransaction = new Intent(getApplicationContext(), Transaction.class);
                            startActivity(goToTransaction);
                        }else{
                            AlertDialog.Builder wrongUserPasswordBuilder = new AlertDialog.Builder(context);
                            wrongUserPasswordBuilder
                                    .setMessage("Your user name and/or password are/is incorrect")
                                    .setTitle("Incorrect Input(s)")
                                    .setCancelable(true)
                                    .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });
                            AlertDialog wrongUserPasswordDialog = wrongUserPasswordBuilder.create();
                            wrongUserPasswordDialog.show();

                        }
                        signIn.dismiss();
                    }
                });

    }

    @Override
    public void onBackPressed() {
        // do not allow android backward button


        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            System.err.println("online");
        } else {
            System.err.println("offline");
        }


        RequestQueue ExampleRequestQueue = Volley.newRequestQueue(this);

        String url = "https://www.google.com/search?q=Android+GET+request";
        UserApi user = new UserApi();

        url = "https://petstore.swagger.io/v2/user/test";
        //ApiInvoker apiInvoker = ApiInvoker.getInstance();




        StringRequest ExampleStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                System.err.println(response);
                try {
                    User user1 = new User();
                    user1 = (User) ApiInvoker.deserialize(response, "", User.class);

                    System.err.println(user1.getFirstName());



            } catch (ApiException ex) {

            }


                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                //You can test it by printing response.substring(0,500) to the screen.
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
                System.err.println("error");
            }
        });

        ExampleRequestQueue.add(ExampleStringRequest);



        StoreApi store = new StoreApi();


        try {
            //apiInstance.addPet(body);

            //apiInstance.addPet(body) ;

            //  store.getInventory().toString();
            //HttpBasicAuth auth = new HttpBasicAuth();
            //auth.setPassword("12345");
            //auth.setUsername("test");
            //System.err.println("test " + store.getInventory().toString());
//serApi user = new UserApi();

            String basePath = "https://petstore.swagger.io/v2";
           // ApiInvoker apiInvoker = ApiInvoker.getInstance();


//user.getUserByName("test");


            System.err.println("test " + store.getBasePath());

           // store.setBasePath("http://petstore.swagger.io/v2");
            //Map<String, Integer> result = store.getInventory();
            //System.out.println(result);

        } catch (Exception  e) {
            System.err.println("Exception when calling PetApi#addPet");
            e.printStackTrace();
        }
    }


}

