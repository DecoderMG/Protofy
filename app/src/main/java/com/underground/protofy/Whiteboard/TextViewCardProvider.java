package com.underground.protofy.Whiteboard;

import android.support.annotation.NonNull;
import android.view.View;

import com.Protofy.protofy.R;
import com.dexafree.materialList.card.Card;
import com.dexafree.materialList.card.provider.CardProvider;

/**
 * Created by Dakota on 10/12/2015.
 * Material Card provider to make things look fancy.
 */
public class TextViewCardProvider extends CardProvider<TextViewCardProvider> {

    @Override
    public void render(@NonNull View view, @NonNull Card card) {
        super.render(view, card);

    }
    @Override
    public int getLayout() {
        return R.layout.textview_card;
    }
}

