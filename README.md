SlipLayout
==========

Slide up and down Layout. You can see this - Facebook, Google+... and PoppyView on GitHub

Related Project PoppyView(https://github.com/flavienlaurent/poppyview)

I want to make this another way. and improve sensitivity.

View Demo : http://www.youtube.com/watch?v=SmGJJk2rx3c&feature=youtu.be

SlipLayout slipLayout = (SlipLayout) findViewById(R.id.slip_layout);

ListView listView = (ListView) findViewById(R.id.list_view);

View targetView = findViewById(R.id.v_target);

slipLayout.setListView(listView);

slipLayout.setTargetView(targetView);

It can also support RecyclerView, ScrollView

slipLayout.setRecyclerView(recyclerView);

slipLayout.setScrollView(scrollView);