package metropolesustentavel.view;

import metropolesustentavel.model.*;
import metropolesustentavel.util.Constantes;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.Map;
import java.util.Random;

public class PainelMapa extends JPanel {

    private Map<TipoIndicador, Indicador> indicadores;
    private int nivelAtual = 1;
    private final Timer animTimer;
    private double tick = 0;
    private final Random rnd = new Random(42);

    private final int[] predioX    = {10,60,115,175,230,290,345,400,455,510,570,620,670};
    private final int[] predioLarg = {42,55,48,65,50,58,44,62,52,60,46,54,50};
    private final int[] predioAlt  = {110,155,130,185,120,165,108,150,128,170,115,145,135};
    private final boolean[][] janelaAcesa;

    public PainelMapa() {
        setBackground(Constantes.COR_FUNDO);
        setPreferredSize(new Dimension(480, 320));
        janelaAcesa = new boolean[predioX.length][40];
        for (int i = 0; i < predioX.length; i++)
            for (int j = 0; j < 40; j++)
                janelaAcesa[i][j] = rnd.nextFloat() < 0.65f;
        animTimer = new Timer(33, e -> { tick += 0.04; repaint(); });
        animTimer.start();
    }

    public void atualizar(Map<TipoIndicador, Indicador> ind, int nivel) {
        this.indicadores = ind;
        this.nivelAtual  = nivel;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int w = getWidth(), h = getHeight();
        double pol = val(TipoIndicador.POLUICAO), sat = val(TipoIndicador.SATISFACAO);
        double mob = val(TipoIndicador.MOBILIDADE), enr = val(TipoIndicador.ENERGIA);
        double res = val(TipoIndicador.RESIDUOS);

        desenharCeu(g2, w, h, pol);
        desenharNuvens(g2, w, h, pol);
        desenharSol(g2, w, h, pol, enr);
        desenharChao(g2, w, h);
        desenharPista(g2, w, h);
        desenharPrediosFundo(g2, w, h);
        desenharPredioPrincipal(g2, w, h, enr);
        desenharVegetacao(g2, w, h, sat, res);
        desenharVeiculos(g2, w, h, mob);
        desenharFumaca(g2, w, h, pol);
        desenharPaineisSolares(g2, w, h, enr);
        if (pol >= 75) desenharChuvaAcido(g2, w, h, pol);
        if (sat >= 35) desenharPedestres(g2, w, h, sat);
        desenharHUD(g2, w, h);
        g2.dispose();
    }

    private void desenharCeu(Graphics2D g2, int w, int h, double pol) {
        // "Saude" geral: poluicao baixa + energia + sustentabilidade
        double saudeCeu = (
            (100 - pol) * 0.50 +
            val(TipoIndicador.ENERGIA)          * 0.25 +
            val(TipoIndicador.SUSTENTABILIDADE) * 0.25
        ) / 100.0;

        // Topo: poluido = cinza-esverdeado escuro  ->  limpo = azul vivo
        int rT = (int) lerp(60,  30,  saudeCeu);
        int gT = (int) lerp(58,  90,  saudeCeu);
        int bT = (int) lerp(42, 185,  saudeCeu);

        // Horizonte: poluido = amarelo-acinzentado  ->  limpo = azul claro
        int rB = (int) lerp(80,  80,  saudeCeu);
        int gB = (int) lerp(72, 160,  saudeCeu);
        int bB = (int) lerp(48, 220,  saudeCeu);

        GradientPaint gp = new GradientPaint(
            0, 0,           new Color(rT, gT, bT),
            0, (int)(h*.6), new Color(rB, gB, bB));
        g2.setPaint(gp);
        g2.fillRect(0, 0, w, (int)(h * .6));
        g2.setPaint(null);

        // Nevoa de poluicao (some conforme melhora)
        if (pol > 30) {
            int alphaSmog = (int) lerp(0, 120, (pol - 30) / 70.0);
            g2.setColor(new Color(160, 140, 60, alphaSmog));
            g2.fillRect(0, 0, w, (int)(h * .6));
        }
    }

    private void desenharNuvens(Graphics2D g2, int w, int h, double pol) {
        int al=(int)lerp(60,180,pol/100), cz=(int)lerp(200,100,pol/100);
        g2.setColor(new Color(cz,cz,cz,al));
        double[] offs={0,.3,.6}; int[] ys={20,35,15},lgs={90,70,80};
        for(int i=0;i<3;i++){
            double ox=((tick*12+offs[i]*w)%(w+100))-50;
            g2.fillOval((int)ox,ys[i],lgs[i],28); g2.fillOval((int)ox+25,ys[i]-12,lgs[i]-20,24);
            g2.fillOval((int)ox+50,ys[i]+2,lgs[i]-10,22);
        }
    }

    private void desenharSol(Graphics2D g2, int w, int h, double pol, double enr) {
        if(pol>70) return;
        int al=(int)lerp(255,60,pol/100); double r=22+Math.sin(tick*.8)*2;
        int sx=w-70,sy=35;
        g2.setColor(new Color(255,220,80,al/2)); g2.setStroke(new BasicStroke(2.5f));
        for(int i=0;i<8;i++){
            double ang=Math.toRadians(i*45+tick*15);
            g2.drawLine((int)(sx+Math.cos(ang)*(r+4)),(int)(sy+Math.sin(ang)*(r+4)),
                        (int)(sx+Math.cos(ang)*(r+14)),(int)(sy+Math.sin(ang)*(r+14)));
        }
        g2.setStroke(new BasicStroke(1));
        g2.setColor(new Color(255,220,60,al)); g2.fillOval((int)(sx-r),(int)(sy-r),(int)(r*2),(int)(r*2));
        if(enr>60){g2.setColor(new Color(255,255,150,80)); g2.fillOval((int)(sx-r-8),(int)(sy-r-8),(int)(r*2+16),(int)(r*2+16));}
    }

    private void desenharChao(Graphics2D g2, int w, int h) {
        int base=(int)(h*.63);
        g2.setColor(new Color(28,33,40)); g2.fillRect(0,base,w,h-base);
        g2.setColor(new Color(45,50,58)); g2.fillRect(0,base,w,18);
    }

    private void desenharPista(Graphics2D g2, int w, int h) {
        int y=(int)(h*.70);
        g2.setColor(new Color(38,42,50)); g2.fillRect(0,y,w,(int)(h*.09));
        g2.setColor(new Color(255,215,0,130));
        double off=(tick*40)%50;
        for(double x=-50+off;x<w+50;x+=50) g2.fillRect((int)x,y+(int)(h*.04),28,4);
    }

    private void desenharPrediosFundo(Graphics2D g2, int w, int h) {
        int base=(int)(h*.63);
        for(int i=0;i<predioX.length;i++){
            int px=predioX[i],pl=predioLarg[i],pa=predioAlt[i],topo=base-pa;
            g2.setColor(new Color(28,36,50)); g2.fillRect(px,topo,pl,pa);
            for(int row=0;row<8;row++) for(int col=0;col<3;col++){
                int idx=row*5+col; if(idx>=40) break;
                boolean ac=janelaAcesa[i][idx]&&!(Math.sin(tick*.3+i*.7+row)>.97);
                g2.setColor(ac?new Color(255,240,150,180):new Color(20,30,45,120));
                int wx=px+6+col*12,wy=topo+10+row*14;
                if(wx+8<px+pl) g2.fillRect(wx,wy,8,9);
            }
            g2.setColor(new Color(60,70,85)); g2.fillRect(px+pl/2-1,topo-12,2,12);
            double blink=Math.sin(tick*2.5+i);
            if(blink>0){g2.setColor(new Color(255,60,60,(int)(blink*200))); g2.fillOval(px+pl/2-3,topo-14,6,6);}
        }
    }

    private void desenharPredioPrincipal(Graphics2D g2, int w, int h, double enr) {
        int base=(int)(h*.63),px=w/2-35,pl=70,pa=200,topo=base-pa;
        Color c1=enr>50?new Color(20,50,80):new Color(35,42,55);
        g2.setPaint(new GradientPaint(px,topo,c1,px+pl,base,new Color(28,36,50)));
        g2.fillRect(px,topo,pl,pa); g2.setPaint(null);
        g2.setColor(new Color(60,80,110)); g2.setStroke(new BasicStroke(1.5f));
        g2.drawRect(px,topo,pl,pa); g2.setStroke(new BasicStroke(1));
        for(int row=0;row<12;row++) for(int col=0;col<4;col++){
            boolean ac=janelaAcesa[0][(row*4+col)%40];
            g2.setColor(ac?new Color(100,200,255,200):new Color(20,35,55));
            g2.fillRect(px+8+col*14,topo+12+row*15,10,10);
        }
        g2.setFont(new Font("Segoe UI",Font.BOLD,7));
        g2.setColor(enr>60?Constantes.COR_VERDE_NEON:Constantes.COR_TEXTO_DIM);
        g2.drawString("PREFEITURA",px+4,topo+8);
        // Símbolo reciclagem no prédio
        g2.setFont(new Font("Segoe UI Emoji",Font.PLAIN,14));
        g2.drawString("♻️",px+22,topo+28);
    }

    private void desenharVegetacao(Graphics2D g2, int w, int h, double sat, double res) {
        int base=(int)(h*.63);
        double verde=((sat/100.0)+((100-res)/100.0))/2.0;
        int qtd=(int)(verde*7);
        int[] posX={15,95,195,310,400,530,620};
        for(int i=0;i<qtd&&i<posX.length;i++){
            int x=posX[i]; double bal=Math.sin(tick*1.2+i*.8)*2;
            g2.setColor(new Color(101,67,33)); g2.fillRect(x+9,base-30,5,25);
            g2.setColor(new Color(27,120,65)); g2.fillOval((int)(x+bal),base-70,24,42);
            g2.setColor(new Color(39,160,85)); g2.fillOval((int)(x+3+bal),base-78,18,36);
            g2.setColor(new Color(46,204,113)); g2.fillOval((int)(x+6+bal*.5),base-82,12,20);
        }
        if(sat>60){g2.setColor(new Color(39,160,85,120)); g2.fillRect(0,base-6,w,6);}
    }

    private void desenharVeiculos(Graphics2D g2, int w, int h, double mob) {
        int y=(int)(h*.705);
        int qtd=Math.min((int)((100-mob)/18.0)+1,5);
        Color[] cores={Constantes.COR_VERMELHO,Constantes.COR_AZUL,
                       new Color(200,200,200),Constantes.COR_AMARELO,new Color(150,80,200)};
        double speed=lerp(4,25,mob/100);
        for(int i=0;i<qtd;i++){
            double cx=((tick*speed+i*(w/qtd))%(w+80))-40; Color c=cores[i%cores.length];
            g2.setColor(c); g2.fillRoundRect((int)cx,y-12,34,13,6,6);
            g2.setColor(c.brighter()); g2.fillRoundRect((int)cx+7,y-19,20,9,4,4);
            g2.setColor(new Color(150,210,255,160));
            g2.fillRect((int)cx+9,y-18,7,7); g2.fillRect((int)cx+18,y-18,7,7);
            g2.setColor(Color.DARK_GRAY);
            g2.fillOval((int)cx+4,y+1,9,9); g2.fillOval((int)cx+21,y+1,9,9);
            g2.setColor(new Color(255,255,180,200)); g2.fillOval((int)cx+29,y-8,5,4);
        }
        if(mob>55){
            double bx=((tick*(speed*.6)+w*.6)%(w+120))-60;
            g2.setColor(new Color(0,130,220)); g2.fillRoundRect((int)bx,y-22,65,22,5,5);
            g2.setColor(new Color(150,220,255,180));
            for(int j=0;j<4;j++) g2.fillRect((int)bx+5+j*14,y-20,10,12);
            g2.setColor(Color.WHITE); g2.setFont(new Font("Segoe UI",Font.BOLD,8));
            g2.drawString("BRT",(int)bx+24,y-5);
            g2.setColor(Color.DARK_GRAY);
            g2.fillOval((int)bx+8,y+1,10,10); g2.fillOval((int)bx+45,y+1,10,10);
        }
        if(mob>70){
            double bx=((tick*(speed*1.4)+50)%(w+40))-20;
            g2.setColor(Constantes.COR_VERDE); g2.setStroke(new BasicStroke(2));
            g2.drawOval((int)bx,y-1,10,10); g2.drawOval((int)bx+14,y-1,10,10);
            g2.drawLine((int)bx+5,y+4,(int)bx+12,y-5); g2.drawLine((int)bx+12,y-5,(int)bx+19,y+4);
            g2.setColor(Constantes.COR_TEXTO); g2.fillOval((int)bx+10,y-14,7,7);
            g2.setStroke(new BasicStroke(1));
        }
    }

    private void desenharFumaca(Graphics2D g2, int w, int h, double pol) {
        if(pol<25) return;
        int base=(int)(h*.63); int[] cx={75,220,370,550};
        for(int i=0;i<cx.length;i++){
            if(pol<25+i*15) continue;
            for(int b=0;b<5;b++){
                double ph=tick*.7+b*.4+i*.9;
                double px=cx[i]+Math.sin(ph*1.1)*8, py=base-180-(ph%3.5)*28;
                double r=14+b*4+Math.sin(ph)*3;
                int al=Math.max(0,(int)(pol*1.8)-b*25),cz=(int)lerp(140,60,pol/100);
                g2.setColor(new Color(cz,Math.max(0,cz-10),Math.max(0,cz-20),Math.min(al,160)));
                g2.fillOval((int)(px-r/2),(int)py,(int)r,(int)r);
            }
        }
    }

    private void desenharPaineisSolares(Graphics2D g2, int w, int h, double enr) {
        if(enr<40) return;
        int base=(int)(h*.63); int[] tx={62,175,345,455},ta={155,130,108,128};
        for(int i=0;i<tx.length;i++){
            if(enr<40+i*12) continue;
            int px=tx[i],topo=base-ta[i]-2;
            double br=.6+Math.sin(tick*1.5+i)*.4; int al=(int)(br*200);
            g2.setColor(new Color(0,100,220,al)); g2.fillRect(px,topo,30,10);
            g2.setColor(new Color(30,150,255,al/2));
            g2.fillRect(px+1,topo+1,13,8); g2.fillRect(px+16,topo+1,13,8);
            g2.setColor(new Color(0,60,140,al));
            g2.drawLine(px+15,topo,px+15,topo+10); g2.drawLine(px,topo+5,px+30,topo+5);
        }
    }

    private void desenharChuvaAcido(Graphics2D g2, int w, int h, double pol) {
        g2.setColor(new Color(180,200,50,(int)((pol-75)*4)));
        double off=(tick*30)%20;
        for(int x=0;x<w;x+=12) for(int y=0;y<(int)(h*.6);y+=20){
            double dy=(y+off)%(h*.6);
            g2.drawLine(x,(int)dy,x-2,(int)(dy+10));
        }
    }

    private void desenharPedestres(Graphics2D g2, int w, int h, double sat) {
        int y=(int)(h*.635); int qtd=(int)(sat/25.0);
        double[] sp={8,-6,10,-7};
        for(int i=0;i<qtd&&i<4;i++){
            double px=((tick*sp[i]+i*120+w)%(w+20))-10;
            g2.setColor(new Color(220,180,140)); g2.fillOval((int)px,y-16,7,7);
            g2.setColor(new Color(80+i*40,100+i*20,180-i*30));
            g2.drawLine((int)px+3,y-9,(int)px+3,y-2);
            double passo=Math.sin(tick*5+i)*3;
            g2.drawLine((int)px+3,y-2,(int)(px+3+passo),y+5);
            g2.drawLine((int)px+3,y-2,(int)(px+3-passo),y+5);
        }
    }

    private void desenharHUD(Graphics2D g2, int w, int h) {
        g2.setFont(new Font("Segoe UI",Font.BOLD,11));
        g2.setColor(new Color(255,255,255,70));
        g2.drawString("♻️ Metrópole Sustentável · Nível "+nivelAtual, 8, h-8);
    }

    private double val(TipoIndicador t) { return indicadores==null?50:indicadores.get(t).getValor(); }
    private double lerp(double a, double b, double t) { return a+(b-a)*Math.max(0,Math.min(1,t)); }
    public void parar() { animTimer.stop(); }
}
