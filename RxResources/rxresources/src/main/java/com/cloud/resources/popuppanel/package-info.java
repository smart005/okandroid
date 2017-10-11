/**
 * 下拉面板方式一
 * private BaseDropPanel mdpanel = new BaseDropPanel();
 * @Override
 *	public void onClick(View v) {
 *		mdpanel.setContentView(R.layout.popup_test_content_view);
 *		mdpanel.setWidth(LayoutParams.MATCH_PARENT);
 *		mdpanel.showAsDropPanel(PopupTest.this, v, 0, 0);
 *	}
 * 下拉面板方式二
 * private PopupTestDialog mptdg = new PopupTestDialog();
 * @Override
 *	public void onClick(View v) {
 *		mptdg.show(PopupTest.this, v, 0, 0);
 *	}
 * public class PopupTestDialog extends BaseDropPanel {
 *	private ListView mpopuptestlv = null;
 *	private List<String> datalist = new ArrayList<String>();
 *	private Context currcontext = null;
 *	private PopupTestAdapter curradapter = null;
 *	
 *	public void show(Context context, View anthor, int xoff, int yoff) {
 *		this.currcontext = context;
 *		this.setContentView(R.layout.popup_test_content_view);
 *		this.setWidth(LayoutParams.MATCH_PARENT);
 *		this.setOnDropPanelListener(mdplistener);
 *		if (datalist.size() <= 0) {
 *			getDatas();
 *		}
 *		this.showAsDropPanel(context, anthor, xoff, yoff);
 *		curradapter.notifyDataSetChanged();
 *	}
 *	
 *	private void getDatas() {
 *		datalist.add("生活");
 *		datalist.add("家具");
 *		datalist.add("电影");
 *		datalist.add("书集");
 *		datalist.add("家电");
 *	}
 *	
 *	private DropPanelListener mdplistener = new DropPanelListener() {
 *		@Override
 *		public void onDropPanelView(View contentView) {
 *			mpopuptestlv = (ListView) contentView
 *					.findViewById(R.id.popup_test_lv);
 *			curradapter = new PopupTestAdapter();
 *			mpopuptestlv.setAdapter(curradapter);
 *		}
 *	};
 *	
 *	private class PopupTestViewHolder {
 *		public TextView mText = null;
 *	}
 *	
 *	private class PopupTestAdapter extends BaseAdapter {
 *		@Override
 *		public int getCount() {
 *			return datalist.size();
 *		}
 *	
 *		@Override
 *		public Object getItem(int position) {
 *			return datalist.get(position);
 *		}
 *	
 *		@Override
 *		public long getItemId(int position) {
 *			return position;
 *		}
 *	
 *		@Override
 *		public View getView(int position, View convertView, ViewGroup parent) {
 *			try {
 *				PopupTestViewHolder holder = null;
 *				if (convertView == null) {
 *					holder = new PopupTestViewHolder();
 *					convertView = View.inflate(currcontext,
 *							R.layout.popup_test_item_view, null);
 *					holder.mText = (TextView) convertView;
 *					convertView.setTag(holder);
 *				} else {
 *					holder = (PopupTestViewHolder) convertView.getTag();
 *				}
 *				holder.mText.setText(datalist.get(position));
 *			} catch (Exception e) {
 *				// write log
 *			}
 *			return convertView;
 *		}
 *	}
 * 上推面板方式一
 * private BaseUpwardPanel mupanel = new BaseUpwardPanel();
 * private UpwardPanelListener muplistener = new UpwardPanelListener() {
 *		@Override
 *		public void onUpwardPanelView(View contentView) {
 *
 *		}
 *	};
 *	@Override
 *	public void onClick(View v) {
 *		mupanel.setContentView(R.layout.popup_test_content_view);
 *		mupanel.setUpwardPanelListener(muplistener);
 *		mupanel.show(PopupTest.this);
 *	}
 * 上推面板方式二
 * private PicMenuBox mpmbox = new PicMenuBox() {
 *		@Override
 *		public void onItemListener(View v, CmdItem entity) {
 *
 *		}
 *	};
 *	mpmbox.show(PopupTest.this);
 *	public class PicMenuBox {
 *
 *		public String TAKING = "f71fbcfc5c45493ea120843bd295b1d1";
 *		public String ALBUM = "b30c547ac3ca4fa992fe98a0b553c26f";
 *	
 *		public PicMenuBox() {
 *			mbmenu.setCancelClickOutLayout(false);
 *			mbmenu.setFirstItemBackgroundResid(R.drawable.pic_menu_first_item_bg);
 *			mbmenu.setItemBackgroundResid(R.drawable.pic_menu_item_bg);
 *			mbmenu.setEndItemBackgroundResid(R.drawable.pic_menu_end_item_bg);
 *			mbmenu.setMenuBackgroundResid(R.drawable.pic_menu_bg);
 *			mbmenu.setSplitLineColor(R.color.pic_menu_split_line_color);
 *		}
 *	
 *		public void onItemListener(View v, CmdItem entity) {
 *	
 *		}
 *	
 *		private List<MenuDialogItem> buildItems(Context context) {
 *			List<MenuDialogItem> lst = new ArrayList<MenuDialogItem>();
 *			MenuDialogItem mtaking = new MenuDialogItem();
 *			String mtakingname = context.getString(R.string.taking_text);
 *			mtaking.setCmdItem(new CmdItem(TAKING, mtakingname));
 *			mtaking.setTextColorResid(R.color.pic_menu_item_text_color);
 *			mtaking.setTextName(mtakingname);
 *			lst.add(mtaking);
 *			MenuDialogItem album = new MenuDialogItem();
 *			String albumname = context.getString(R.string.album_select_text);
 *			album.setCmdItem(new CmdItem(ALBUM, albumname));
 *			album.setTextColorResid(R.color.pic_menu_item_text_color);
 *			album.setTextName(albumname);
 *			lst.add(album);
 *			return lst;
 *		}
 *	
 *		private BaseUpwardMenu mbmenu = new BaseUpwardMenu() {
 *			@Override
 *			public void onClickItemListener(View v, CmdItem entity) {
 *				onItemListener(v, entity);
 *			}
 *		};
 *	
 *		public void show(Context context) {
 *			mbmenu.setDataItems(buildItems(context));
 *			mbmenu.setCancelClickOutLayout(false);
 *			mbmenu.show(context);
 *		}
 *	}
 */
/**
 * @author lijinghuan
 *
 */
package com.cloud.resources.popuppanel;

