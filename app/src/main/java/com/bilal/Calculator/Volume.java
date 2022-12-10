package com.bilal.Calculator;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ayncalculator.R;
import com.google.android.material.navigation.NavigationView;

public class Volume extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;

    private TextView expr,result,ce,openBrc,closeBrc,div,mul,minus,plus,dot,back,equals;
    private TextView zero,one,two,three,four,five,six,seven,eight,nine;
    private TextView topLvl,btmLvl,rev,sin,cos,tan,pow,sqroot,fact;

    private boolean g2l = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volume);


        // id linking
        expr = findViewById(R.id.expr);
        result = findViewById(R.id.result);
        ce = findViewById(R.id.ce);
        dot = findViewById(R.id.dot);
        back = findViewById(R.id.back);
        equals = findViewById(R.id.equals);
        //fact = findViewById(R.id.fact);


        //drawer

        Toolbar toolbar = findViewById(R.id.toolbar);



        drawerLayout = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(Volume.this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);//enable



        zero = findViewById(R.id.zero);
        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        three = findViewById(R.id.three);
        four = findViewById(R.id.four);
        five = findViewById(R.id.five);
        six = findViewById(R.id.six);
        seven = findViewById(R.id.seven);
        eight = findViewById(R.id.eight);
        nine = findViewById(R.id.nine);
        topLvl = findViewById(R.id.topLvl);
        btmLvl = findViewById(R.id.btmLvl);
        rev = findViewById(R.id.rev);


        // Numbers
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendOnExpr("1",true);
            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendOnExpr("2",true);
            }
        });
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendOnExpr("3",true);
            }
        });
        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendOnExpr("4",true);
            }
        });
        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendOnExpr("5",true);
            }
        });
        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendOnExpr("6",true);
            }
        });
        seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendOnExpr("7",true);
            }
        });
        eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendOnExpr("8",true);
            }
        });
        nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendOnExpr("9",true);
            }
        });
        zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendOnExpr("0",true);
            }
        });
        dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendOnExpr(".",true);
            }
        });


        rev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(g2l) {
                    topLvl.setText("L");
                    btmLvl.setText("G");
                } else {
                    topLvl.setText("G");
                    btmLvl.setText("L");
                }
                g2l = !g2l;
                try {
                    double input = Double.parseDouble(expr.getText().toString());
                    double reslt;
                    if(g2l){
                        reslt = 3.78541*input;
                    } else {
                        reslt = 0.264172*input;
                    }
                    int intres = (int) reslt;
                    if (reslt == intres) {
                        result.setText(Integer.toString(intres));
                    } else {
                        result.setText(Double.toString(reslt));
                    }
                } catch (Exception e) {
                    result.setText("Err !");
                }
            }
        });




        //Operators
        ce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expr.setText("");
                result.setText("");
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String string = expr.getText().toString();
                if(!string.isEmpty()){
                    expr.setText(string.substring(0,string.length()-1));
                }
                result.setText("");
            }
        });


        equals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    double input = Double.parseDouble(expr.getText().toString());
                    double reslt;
                    String calcType;
                    if(g2l){
                        calcType = " (m^3 to Litre)";
                        reslt = 1000*input; // Celcious to Faren
                    } else {
                        calcType = " (m^3 to Gallon)";
                        reslt = input/1000 ;
                    }
                    int intres = (int) reslt;
                    String finalResult;
                    if (reslt == intres) {
                        result.setText(Integer.toString(intres));
                        finalResult = Integer.toString(intres);
                    } else {
                        result.setText(Double.toString(reslt));
                        finalResult = Double.toString(reslt);
                    }

                    try {
                        dbMiddleware dbM = new dbMiddleware(expr.getText().toString(), finalResult, "Temperature" + calcType);
                        dbM.writeDB();
                        Toast.makeText(Volume.this, "Stored in Database!", Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(Volume.this, "Failed to Store in Database!", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    result.setText("Err!");
                }
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem Item){
        if(toggle.onOptionsItemSelected(Item))
            return true;
        return super.onContextItemSelected(Item);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        if (menuItem.isChecked()){
            drawerLayout.closeDrawer(GravityCompat.START);
            return false;
        }

        int id = menuItem.getItemId();

        if(id==R.id.nav_standard) {
            /*Toast.makeText(this, "The Temp", Toast.LENGTH_SHORT).show();*/
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        } else if(id==R.id.nav_temp){
            /*Toast.makeText(this, "The Temp", Toast.LENGTH_SHORT).show();*/
            Intent intent = new Intent(getApplicationContext(), Temp.class);
            startActivity(intent);
        } else if(id==R.id.nav_weight){
            Intent intent = new Intent(getApplicationContext(), Weight.class);
            startActivity(intent);
        } else if(id==R.id.nav_currency){
            Intent intent = new Intent(getApplicationContext(), Currency.class);
            startActivity(intent);
        }else if(id==R.id.nav_length) {
            Intent intent = new Intent(getApplicationContext(), Length.class);
            startActivity(intent);
        }  else if(id==R.id.nav_volume){
            Intent intent = new Intent(getApplicationContext(), Volume.class);
            startActivity(intent);
        } else if(id==R.id.nav_us){
            Intent intent = new Intent(getApplicationContext(), AboutUs.class);
            startActivity(intent);
        } else if(id==R.id.nav_history) {
            Intent intent = new Intent(getApplicationContext(), History.class);
            startActivity(intent);
        } else if(id==R.id.nav_exit){
            finish();
        }
        return false;
    }
    @Override
    protected void onDestroy() {
        Process.killProcess(Process.myPid());
        super.onDestroy();
    }

    void appendOnExpr(String string , Boolean canClear){
        if(!result.getText().toString().isEmpty()){
            expr.setText("");
        }

        if(canClear){
            result.setText("");
            expr.append(string);
        }else{
            expr.append(result.getText());
            expr.append(string);
            result.setText("");
        }
    }
}
