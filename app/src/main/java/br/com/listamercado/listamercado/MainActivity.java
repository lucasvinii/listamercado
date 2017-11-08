package br.com.listamercado.listamercado;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView list_view;
    EditText txt_produto;

    ProdutoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list_view = (ListView) findViewById(R.id.list_view);
        txt_produto = (EditText) findViewById(R.id.produto);

        List<Produto> lstProduto = Produto.listAll(Produto.class);

        //criar o adapter
        adapter = new ProdutoAdapter(this, lstProduto);

        //ligar o adapter ao list view
        list_view.setAdapter(adapter);

        adapter.add(lstProduto);

    }

    public void inserirItem(View view) {

        String produto = txt_produto.getText().toString();

        if (produto.isEmpty()) return;

        //criando o obj produto
        Produto p = new Produto(produto, false);

        //adicionando na lista
        adapter.add(p);

        //adicionando no banco
        p.save();

        //limpar a caixinha
        txt_produto.setText(null);
    }


    private class ProdutoAdapter extends ArrayAdapter<Produto>{

        public ProdutoAdapter(Context ctx, List<Produto> items){
            super(ctx, 0, items);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView,
                            @NonNull ViewGroup parent) {

            View v = convertView;

            if (v == null){

                v = LayoutInflater.from(getContext())
                        .inflate(R.layout.item_lista, null);
            }
                Produto item = getItem(position);

                TextView txt_item_produto = v.findViewById(R.id.txt_item_produto);
                CheckBox ck_item_produto = v.findViewById(R.id.ck_item_produto);

                txt_item_produto.setText(item.getNome());
                ck_item_produto.setChecked(item.isAtivo());


                return v;

        }
    }
}
